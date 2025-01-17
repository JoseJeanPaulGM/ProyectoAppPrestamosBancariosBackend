package com.cibertec.prestamos.controller;

import com.cibertec.prestamos.domain.model.Grupo;
import com.cibertec.prestamos.domain.model.GrupoPrestamista;
import com.cibertec.prestamos.dto.GrupoDto;
import com.cibertec.prestamos.service.IGrupoPrestamistaService;
import com.cibertec.prestamos.util.AppSettings;
import com.cibertec.prestamos.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cibertec.prestamos.service.IGrupoService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/grupo")
@CrossOrigin(origins = AppSettings.CrossOriginUrl)
public class GrupoController {

    @Autowired
    private IGrupoService grupoService;

    @Autowired
    private IGrupoPrestamistaService grupoPrestamistaService;

    private Logger log = LoggerFactory.getLogger(GrupoController.class);

    @GetMapping(path = "/listar")
    public ResponseEntity<Response> listar() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new Response(grupoService.obtenerTodosLosGrupos(), "Lista de grupos."));
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error al listar los grupos."));
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response> listarPorGrupo(@PathVariable("id") int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new Response(grupoService.obtenerGrupoPorId(id), "Lista de grupos del Id = [ " + id + " ]."));
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error al listar el grupo = [ " + id + " ]."));
        }
    }

    @PostMapping(path = "/registrar")
    public ResponseEntity<Response> registrar(@RequestBody GrupoDto grupo) {
        try {
            Grupo newGrupo = new Grupo();
            newGrupo.setDescripcion(grupo.getDescripcion());
            newGrupo.setEstado(grupo.getEstado());
            newGrupo.setFechaCreacion(Date.from(java.time.ZonedDateTime.now().toInstant()));
            newGrupo.setUsuarioCreacion(grupo.getUsuarioCreacion());

            return ResponseEntity.status(HttpStatus.OK).body(new Response(grupoService.guardarGrupo(newGrupo), "Grupo registrado."));
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error al registrar el grupo."));
        }
    }

    @GetMapping(path = "/prestamistas/{id}")
    public ResponseEntity<Response> listarPrestamistasPorGrupo(@PathVariable("id") int id) {
        try {
            Optional<Grupo> grupo = grupoService.obtenerGrupoPorId(id);
            if (grupo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new Response("No se encontró el grupo Id = [ " + id + " ]."));
            }
            List<GrupoPrestamista> listaGrupoPrestamista = grupoPrestamistaService.obtenerPrestamistasPorGrupo(grupo.get().getIdGrupo());

            return ResponseEntity.status(HttpStatus.OK).body(new Response(listaGrupoPrestamista, "Lista de prestamistas del grupo Id = [ " + id + " ]."));
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error al listar los prestamistas del grupo Id = [ " + id + " ]."));
        }
    }


    //Obtener grupo de un Jefe de Prestamista
    @GetMapping(path = "/jefePrestamista/{id}")
    public ResponseEntity<Response> listarGrupoPorJefePrestamista(@PathVariable("id") int id) {
        try {
            Optional<Grupo> grupo = grupoService.obtenerGrupoPorIdJefePrestamista(id);
            if (!grupo.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(new Response("No se encontró el Jefe de Prestamis - Id = [ " + id + " ]."));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new Response(grupo, "Grupo del Jefe de prestamista Id = [ " + id + " ]."));
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error al listar el grupo del Jefe de prestamista Id = [ " + id + " ]."));
        }
    }

    //obterner grupo de un prestamista
    @GetMapping(path = "/prestamista/{id}")
    public ResponseEntity<Response> listarGrupoPorPrestamista(@PathVariable("id") int id) {
        try {
            GrupoPrestamista grupoPrestamista = grupoPrestamistaService.obtenerGrupoPorIdPrestamista(id);
            if (grupoPrestamista == null) {
                return ResponseEntity.status(HttpStatus.OK).body(new Response("No se encontró el prestamista - Id = [ " + id + " ]."));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new Response(grupoPrestamista, "Grupo del prestamista Id = [ " + id + " ]."));
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error al listar el grupo del prestamista Id = [ " + id + " ]."));
        }
    }




}
