package com.algaworks.algafood.infrastructure.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.DailySalesFilter;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.dto.DailySales;
import com.algaworks.algafood.domain.service.SalesQueryService;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 5/30/21 1:08 PM
 */
@Repository
public class SalesQueryServiceImpl implements SalesQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DailySales> findDailySales(final DailySalesFilter filter) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(DailySales.class);
        var root = query.from(Order.class);

        var functionDateOnCreationDate = builder.function(
                "date", Date.class, root.get("creationDate")
        );

        var selection = builder.construct(DailySales.class,
                functionDateOnCreationDate,
                builder.count(root.get("id")),
                builder.sum(root.get("totalValue"))
        );

        query.select(selection);
        query.groupBy(functionDateOnCreationDate);

        return entityManager.createQuery(query).getResultList();
    }

//    public List<VendaDiaria> findDailySalesJpqlExemple(VendaDiariaFilter filtro) {
//        StringBuilder jpql = new StringBuilder(
//                "SELECT new com.algaworks.algafood.domain.model.dto.VendaDiaria(" +
//                        "FUNCTION('date', p.dataCriacao), COUNT(p.id), SUM(p.valorTotal)) " +
//                        "FROM Pedido p ");
//
//        String where = "";
//        boolean setDataCriacaoInicio = false;
//        boolean setDataCriacaoFim = false;
//        boolean setRestauranteId = false;
//
//        if (filtro.getDataCriacaoInicio() != null) {
//            where += "p.dataCriacao >= :dataCriacaoInicio ";
//            setDataCriacaoInicio = true;
//        }
//        if (filtro.getDataCriacaoFim() != null) {
//            if (setDataCriacaoInicio) {
//                where += "AND ";
//            }
//
//            where += "p.dataCriacao <= :dataCriacaoFim ";
//            setDataCriacaoFim = true;
//        }
//        if (filtro.getRestauranteId() != null) {
//            if (setDataCriacaoInicio || setDataCriacaoFim) {
//                where += "AND ";
//            }
//
//            where += "p.restaurante.id = :restauranteId ";
//            setRestauranteId = true;
//        }
//        if (!where.isBlank()) {
//            jpql.append("WHERE ").append(where);
//        }
//
//        jpql.append("GROUP BY FUNCTION('date', p.dataCriacao)");
//        TypedQuery<VendaDiaria> query = manager.createQuery(jpql.toString(), VendaDiaria.class);
//
//        if (setDataCriacaoInicio) {
//            query.setParameter("dataCriacaoInicio", filtro.getDataCriacaoInicio());
//        }
//        if (setDataCriacaoFim) {
//            query.setParameter("dataCriacaoFim", filtro.getDataCriacaoFim());
//        }
//        if (setRestauranteId) {
//            query.setParameter("restauranteId", filtro.getRestauranteId());
//        }
//
//        return query.getResultList();
//    }
}
