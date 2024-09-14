package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();


            Factura factura1 = new Factura();
            factura1.setFecha("12/08/2024");
            factura1.setNumero(33243);

            Domicilio domic1 = new Domicilio();
            domic1.setNombreCalle("Calle Mitre ");
            domic1.setNumero(7890);
            Cliente cliente1 =  Cliente.builder()
                    .nombre("Ana")
                    .apellido("García")
                    .dni(36789012)
                    .domicilio(domic1)
                    .build();

            factura1.setCliente(cliente1);

            Categoria beb = new Categoria();
            beb.setDenominacion("Bebidas");
            //Categoria lac = new Categoria();
            //lac.setDenominacion("Lacteos");
            Categoria sna = new Categoria();
            sna.setDenominacion("Snacks & Golosinas");
           // Categoria per = new Categoria();
            //per.setDenominacion("No Perecedero");
            Articulo art1 = Articulo.builder()
                    .cantidad(130)
                    .denominacion("Vino Nalbec Catena Zapata")
                    .precio(3600)
                    .build();
            Articulo art2 = Articulo.builder()
                    .cantidad(800)
                    .denominacion("Alfajor Havana")
                    .precio(3500)
                    .build();

            art1.getCategorias().add(beb);
            art2.getCategorias().add(sna);
            DetalleFactura detalleF1 = new DetalleFactura();
            DetalleFactura detalleF2 = new DetalleFactura();
            detalleF1.setArticulo(art1);
            detalleF1.setCantidad(5);
            detalleF1.setSubtotal(18000);
            detalleF2.setArticulo(art2);
            detalleF2.setCantidad(9);
            detalleF2.setSubtotal(31500);
            factura1.getDetalle().add(detalleF1);
            factura1.getDetalle().add(detalleF2);
            factura1.setTotal(49500);
            entityManager.persist(factura1);
            entityManager.getTransaction().commit();


            //actualiza la factura
            entityManager.getTransaction().begin();
            factura1.setNumero(58);
            entityManager.getTransaction().commit();


            //elimina la factura
            entityManager.getTransaction().begin();
            entityManager.remove(factura1);
            entityManager.getTransaction().commit();











        }catch (Exception e) {

            entityManager.getTransaction().rollback();

            // Cerrar el EntityManager y el EntityManagerFactory
        }
        entityManager.close();
        entityManagerFactory.close();

    }
}
