package com.ian.figa.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.ian.figa.model.Usuario;
import com.ian.figa.model.dto.UsuarioDTO;
import com.ian.figa.service.UsuarioService;
import com.ian.figa.service.exception.DataIntegrityViolationException;
import com.ian.figa.service.exception.ObjectNotFoundException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UsuarioServiceImpl implements UsuarioService {
    private static final String COLLECTION_NAME = "usuario";

    @Autowired
    private ModelMapper mapper;

    @Override
    public Usuario findById(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(id);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                return document.toObject(Usuario.class);
            } else {
                throw new ObjectNotFoundException("Objeto não encontrado");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> findAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> querySnapshotApiFuture = dbFirestore.collection(COLLECTION_NAME).get();

            List<QueryDocumentSnapshot> queryDocumentSnapshots = querySnapshotApiFuture.get().getDocuments();

            return queryDocumentSnapshots.stream()
                    .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Usuario.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public String create(UsuarioDTO usuarioDto) {
        findByEmail(usuarioDto);
        try {
            // tenho que gerar assim pois o firestore não faz o auto increment
            usuarioDto.setId(generateLastId(findAll()));

            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME)
                    .document(String.valueOf(usuarioDto.getId()))
                    .set(mapper.map(usuarioDto, Usuario.class));

            return collectionApiFuture.get().getUpdateTime().toString();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public String update(String id, UsuarioDTO usuarioDto) {
        findByEmail(usuarioDto);
        try {
            usuarioDto.setId(Integer.parseInt(id));

            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME)
                    .document(id)
                    .set(mapper.map(usuarioDto, Usuario.class));

            return collectionApiFuture.get().getUpdateTime().toString();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(String id) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(COLLECTION_NAME).document(id).delete();
    }

    private void findByEmail(UsuarioDTO obj) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> querySnapshotApiFuture = dbFirestore.collection(COLLECTION_NAME)
                    .whereEqualTo("email", obj.getEmail()).get();

            if (querySnapshotApiFuture.get().getDocuments().size() > 0) {
                throw new DataIntegrityViolationException("E-mail já cadastrado");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public int generateLastId(List<Usuario> usuarios) {
        if (usuarios == null || usuarios.size() == 0) {
            return 1;
        } else {
            return usuarios.get(usuarios.size() - 1).getId() + 1;
        }
    }
}
