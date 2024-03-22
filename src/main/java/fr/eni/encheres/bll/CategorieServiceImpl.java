package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;

@Service
public class CategorieServiceImpl implements CategorieService {
	
	@Autowired
	private CategorieDAO categorieDAO;
	
	public CategorieServiceImpl(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }
	
	@Override
	public List<Categorie> consulterCategories() {
		return categorieDAO.findAll();
	}

}
