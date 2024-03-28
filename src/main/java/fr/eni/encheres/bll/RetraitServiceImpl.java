package fr.eni.encheres.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.RetraitDAO;

@Service
@Primary
public class RetraitServiceImpl implements RetraitService{

	@Autowired
	private RetraitDAO retraitDAO;
	
	public RetraitServiceImpl(RetraitDAO retraitDAO) {
		this.retraitDAO = retraitDAO;
	}
	
	@Override
	public Object creerRetrait(Retrait retrait) {
		return retraitDAO.ajoutRetrait(retrait);
	}
	
	@Override
	public Object modifierRetrait(Retrait retrait) {
		return retraitDAO.modifierRetrait(retrait);
	}
	
}
