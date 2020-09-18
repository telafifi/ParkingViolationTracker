package processor;

import java.util.*;
import data.*;

public interface PropertyValueCollector { //interface to collect values from property list
	/**
	 * Get the total value of a property from provided list in a zip code
	 * @param zipCode
	 * @param properties
	 * @return
	 */
	public double getTotalValue(String zipCode, List<Property> properties);
	/**
	 * Get the number of valid properties from a provided list in a zip code
	 * @param zipCode
	 * @return
	 */
	public int getNumberOfValidProperties(String zipCode);
}
