package br.com.will.service;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import br.com.will.dto.PessoaDTO;
import br.com.will.model.Pessoa;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.runtime.util.StringUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Path("")
@ApplicationScoped
public class PessoaService {

    private static final WebApplicationException RESPONSE_422 = new WebApplicationException(422);

    static LinkedHashMap<String, Pessoa> pessoasPorApelido = new LinkedHashMap<>(60_000);
    static LinkedHashMap<UUID, Pessoa> pessoasPorID = new LinkedHashMap<>(60_000);

    @POST
    @Path("pessoas")
    @WithTransaction
    public Uni<Response> post(PessoaDTO dto) {

        validar(dto);

        return this.duplicado(dto);

    }

    private Uni<Response> duplicado(PessoaDTO dto) {

        String stack = null;

        if (dto.stack != null) {
            for (String st : dto.stack) {
                if (st == null || st.length() > 32) {
                    throw RESPONSE_422;
                }
            }

            // Remover os [] da String que será salva no banco
            String ar = Arrays.toString(dto.stack);
            if (ar != null && ar.trim().length() > 1) {

                stack = ar.substring(1, ar.length() - 1);

                if (stack.length() > 800) {
                    throw RESPONSE_422;
                }
            }
        }

        dto.id = UUID.randomUUID();

        Pessoa pessoa = new Pessoa(dto.id, dto.apelido, dto.nome, dto.nascimento,
                stack);

        return pessoa.persist().onItem().transformToUni(x -> apply(pessoa));
    }

    private void validar(PessoaDTO dto) {

        if (dto == null || dto.id != null) {
            throw RESPONSE_422;
        }

        if (dto.id != null && pessoasPorID.get(dto.id) != null) {
            throw RESPONSE_422;
        }

        if (StringUtil.isNullOrEmpty(dto.apelido) || dto.apelido.length() >= 32
                || (dto.apelido != null && pessoasPorApelido.get(dto.apelido) != null)) {
            throw RESPONSE_422;
        }

        if (StringUtil.isNullOrEmpty(dto.nome) || dto.nome.length() > 100) {
            throw RESPONSE_422;
        }

        if (dto.nascimento == null) {
            throw RESPONSE_422;
        }
    }

    private Uni<Response> apply(Pessoa pessoa) {
       // synchronized (PessoaService.pessoasPorApelido) {
            pessoasPorApelido.put(pessoa.apelido, pessoa);
            pessoasPorID.put(pessoa.id, pessoa);
        //}

        return Uni.createFrom().item(Response.created(URI.create("/pessoas/" + pessoa.id)).build());
    }

    @GET
    @Path("pessoas")
    @WithTransaction
    public Uni<List<Pessoa>> getAll(@QueryParam("t") String termo) {
        if (termo == null || "".equals(termo.trim())) {
            throw new BadRequestException();
        }

        Pessoa p = pessoasPorApelido.get(termo.trim());
        return p != null ? Uni.createFrom().item(Arrays.asList(p))
                : Pessoa.find(
                        "busca ilike '%' || ?1 || '%'",
                        termo).page(0, 10).list();
    }

    // Usado ? para evitar fazer o cast do model
    @GET
    @Path("pessoas/{id}")
    public Uni<?> get(UUID id) {

        Pessoa p = pessoasPorID.get(id);
        return p != null ? Uni.createFrom().item(p)
                : Pessoa.findById(id).onItem().ifNull().failWith(new NotFoundException());
    }

    @GET
    @Path("contagem-pessoas")
    @WithTransaction
    public Uni<Long> count() {

        return Pessoa.count();
    }
}
