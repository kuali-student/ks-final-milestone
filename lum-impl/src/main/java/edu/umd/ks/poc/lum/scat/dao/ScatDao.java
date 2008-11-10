package edu.umd.ks.poc.lum.scat.dao;

import java.util.List;

import edu.umd.ks.poc.lum.scat.entity.Scat;
import edu.umd.ks.poc.lum.scat.entity.ScatTable;

public interface ScatDao {

	public List<ScatTable> searchScats(String searchString);

	public ScatTable createScatTable(ScatTable scatTable);

	public void deleteScatTable(String scatTableId);

	public ScatTable updateScatTable(ScatTable scatTable);

	public ScatTable fetchScatTable(String scatTableId);

	public void deleteScat(Scat scat);

	public Scat createScat(Scat scat);

	public Scat fetchScat(String scatId);

}
