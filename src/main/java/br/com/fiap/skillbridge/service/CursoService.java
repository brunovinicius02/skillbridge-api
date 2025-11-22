package br.com.fiap.skillbridge.service;

import br.com.fiap.skillbridge.model.Curso;
import br.com.fiap.skillbridge.repository.CursoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class CursoService {

    @Inject
    CursoRepository cursoRepository;

    public List<Curso> listarTodos() {
        return cursoRepository.findAtivos();
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));
    }

    public List<Curso> buscarPorArea(String area) {
        return cursoRepository.findByArea(area);
    }

    public List<Curso> buscarPorNivel(String nivel) {
        return cursoRepository.findByNivel(nivel);
    }

    public List<Curso> buscarPorAreaENivel(String area, String nivel) {
        return cursoRepository.findByAreaAndNivel(area, nivel);
    }

    public List<Curso> pesquisarPorNome(String nome) {
        return cursoRepository.searchByNome(nome);
    }

    @Transactional
    public Curso criar(Curso curso) {
        cursoRepository.persist(curso);
        return curso;
    }

    @Transactional
    public Curso atualizar(Long id, Curso cursoAtualizado) {
        Curso curso = buscarPorId(id);
        
        curso.setNome(cursoAtualizado.getNome());
        curso.setDescricao(cursoAtualizado.getDescricao());
        curso.setArea(cursoAtualizado.getArea());
        curso.setNivel(cursoAtualizado.getNivel());
        curso.setCargaHoraria(cursoAtualizado.getCargaHoraria());
        curso.setUrlExterno(cursoAtualizado.getUrlExterno());
        curso.setImagemUrl(cursoAtualizado.getImagemUrl());
        
        cursoRepository.persist(curso);
        return curso;
    }

    @Transactional
    public void deletar(Long id) {
        Curso curso = buscarPorId(id);
        curso.setAtivo("0");
        cursoRepository.persist(curso);
    }
}
