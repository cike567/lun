package org.db;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;

//TOD
public class DruidPersistenceProvider implements PersistenceProvider {

	@Override
	public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateSchema(PersistenceUnitInfo info, Map map) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean generateSchema(String persistenceUnitName, Map map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ProviderUtil getProviderUtil() {
		// TODO Auto-generated method stub
		return null;
	}

}
