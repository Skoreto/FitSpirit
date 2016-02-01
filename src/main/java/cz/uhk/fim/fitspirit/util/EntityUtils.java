
package cz.uhk.fim.fitspirit.util;

import java.util.Collection;

import org.springframework.orm.ObjectRetrievalFailureException;

import cz.uhk.fim.fitspirit.BaseEntity;

/**
 * Utility methods for handling entities. Separate from the BaseEntity class
 * mainly because of dependency on the ORM-associated
 * ObjectRetrievalFailureException.
 */
public abstract class EntityUtils {

	/**
	 * Look up the entity of the given class with the given id in the given
	 * collection.
	 *
	 * @param entities the collection to search
	 * @param entityClass the entity class to look up
	 * @param entityId the entity id to look up
	 * @return the found entity
	 * @throws ObjectRetrievalFailureException if the entity was not found
	 */
	public static <T extends BaseEntity> T getById(Collection<T> entities, Class<T> entityClass, int entityId)
			throws ObjectRetrievalFailureException {
		for (T entity : entities) {
			if (entity.getId().intValue() == entityId && entityClass.isInstance(entity)) {
				return entity;
			}
		}
		throw new ObjectRetrievalFailureException(entityClass, new Integer(entityId));
	}

}
