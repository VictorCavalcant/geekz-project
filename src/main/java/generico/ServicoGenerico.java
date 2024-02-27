/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generico;

/**
 *
 * @author victo
 */



import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victo
 */
public class ServicoGenerico<T> {

    private Class<T> entidade;

    @PersistenceContext
    private EntityManager entityManager;

    public ServicoGenerico(Class<T> entidade) {
        this.entidade = entidade;
    }

    public void salvar(T entidade) {
        entityManager.persist(entidade);
    }

    public void atualizar(T entidade) {
        entityManager.merge(entidade);
    }

    public void remover(T entidade) {

    }
    
   
    public T find(Long id) {
        T objeto = entityManager.find(entidade, id);
        entityManager.refresh(objeto);
        return objeto;    }
    
    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entidade.getName() + " e").getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}

