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
import com.ian.figa.model.Posicao;
import com.ian.figa.model.dto.PosicaoDTO;
import com.ian.figa.service.PosicaoService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PosicaoServiceImpl implements PosicaoService {
    private static final String COLLECTION_NAME = "posicao";

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Posicao> findAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> querySnapshotApiFuture = dbFirestore.collection(COLLECTION_NAME).get();

            List<QueryDocumentSnapshot> queryDocumentSnapshots = querySnapshotApiFuture.get().getDocuments();

            return queryDocumentSnapshots.stream()
                    .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Posicao.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public Posicao findById(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(id);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                return document.toObject(Posicao.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public String create(PosicaoDTO posicaoDto) {
        try {
            // tenho que gerar assim pois o firestore não faz o auto increment
            posicaoDto.setId(generateLastId(findAll()));

            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME)
                    .document(String.valueOf(posicaoDto.getId()))
                    .set(mapper.map(posicaoDto, Posicao.class));

            return collectionApiFuture.get().getUpdateTime().toString();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public String update(String id, PosicaoDTO posicaoDto) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME)
                    .document(id)
                    .set(mapper.map(posicaoDto, Posicao.class));

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

    public int generateLastId(List<Posicao> posicoes) {
        if (posicoes == null || posicoes.size() == 0) {
            return 1;
        } else {
            return posicoes.get(posicoes.size() - 1).getId() + 1;
        }
    }
}
