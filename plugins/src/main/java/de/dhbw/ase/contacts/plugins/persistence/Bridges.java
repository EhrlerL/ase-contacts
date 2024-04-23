package de.dhbw.ase.contacts.plugins.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.ase.contacts.domain.repositories.BridgeRepository;


//Muss das jeweilige Domain Repository implementieren
@Repository
public class Bridges  implements BridgeRepository  {

	@Autowired
	springDataForBridge springDataForBridge;
	
	@Override
	public void deleteById(Long id) {
		// implementieren der "Bridge"
		//z.B. springDataForBridge.deleteById(id);
		
	}



	
	/**
	 * In dem package landen die ganzen Bridges welche benutzt werden zur kommunikation
	 * zwischen den schichten.
	 */
}
