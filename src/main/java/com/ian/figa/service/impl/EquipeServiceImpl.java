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
import com.ian.figa.model.Equipe;
import com.ian.figa.model.dto.EquipeDTO;
import com.ian.figa.service.EquipeService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EquipeServiceImpl implements EquipeService {
    private static final String COLLECTION_NAME = "equipe";

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Equipe> findAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> querySnapshotApiFuture = dbFirestore.collection(COLLECTION_NAME).get();

            List<QueryDocumentSnapshot> queryDocumentSnapshots = querySnapshotApiFuture.get().getDocuments();

            return queryDocumentSnapshots.stream()
                    .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Equipe.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public Equipe findById(String id) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(id);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                return document.toObject(Equipe.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public String create(EquipeDTO equipeDto) {
        // tenho que gerar assim pois o firestore não faz o auto increment
        try {
            equipeDto.setId(generateLastId(findAll()));

            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME)
                    .document(String.valueOf(equipeDto.getId()))
                    .set(mapper.map(equipeDto, Equipe.class));

            return collectionApiFuture.get().getUpdateTime().toString();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public String update(String id, EquipeDTO equipeDto) {
        try {
            equipeDto.setId(Integer.parseInt(id));

            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME)
                    .document(id)
                    .set(mapper.map(equipeDto, Equipe.class));

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

    public int generateLastId(List<Equipe> equipes) {
        if (equipes == null || equipes.size() == 0) {
            return 1;
        } else {
            return equipes.get(equipes.size() - 1).getId() + 1;
        }
    }
}
