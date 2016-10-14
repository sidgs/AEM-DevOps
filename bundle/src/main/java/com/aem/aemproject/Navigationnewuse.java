package com.aem.aemproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.cq.sightly.WCMUse;

public class Navigationnewuse extends WCMUse  {

	/**
	 * Instantiates a new multi composite use.
	 */
	public Navigationnewuse() {
		super();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adobe.cq.sightly.WCMUse#activate()
	 */
	@Override
	public void activate() throws Exception {
		// Do something while activation.
	}
	
	
	
	public List<Map<String,String>> getMultiNavigationList() {
		
		List<Map<String,String>> primaryNavigationList = new ArrayList<Map<String,String>>();		
		final String key = get("key", String.class);
		Resource resource = getRequest().getResource();
		ResourceResolver resourceResolver = resource.getResourceResolver();
		resource = resourceResolver.getResource(key);
		
		
		
		if (null != resource) {
			final Iterator<Resource> mcfChildren = resource.listChildren();
			Resource current = null;
			while (mcfChildren.hasNext()) {
				Map<String,String> listval = new  HashMap<String, String>();
				current = mcfChildren.next();
				//Node node = current.adaptTo(Node.class);
				if (current != null) {
					if(!current.getName().equalsIgnoreCase("jcr:content")){
					listval.put("path", current.getPath() + ".html");
					listval.put("name", current.getName());
					primaryNavigationList.add(listval);
					}
				}
				
			}
		}
		return primaryNavigationList;
	}
	
}
