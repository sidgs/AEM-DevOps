package com.aem.aemproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.adobe.cq.sightly.WCMUse;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;
import com.day.cq.wcm.api.designer.Design;
import com.day.cq.wcm.api.designer.Designer;
import com.day.cq.wcm.api.designer.Style;
import com.day.cq.wcm.commons.WCMUtils;


public class Assetnewuse extends WCMUse {

	/**
	 * Instantiates a new multi composite use.
	 */
	public Assetnewuse() {
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

	/**
	 * Gets the multi field.	
	 * 
	 * @return the multi field
	 */
	public List<ValueMap> getMultiField() {
		final String key = get("key", String.class);
		return getValues(getRequest(), key);
	}

	public List<Map<String, String>> getMultiFieldWithNodes() {
		List<Map<String, String>> tabList = new ArrayList<Map<String,String>>();
		final String key = get("key", String.class);
		Resource parentResource = getResourceResolver().getResource(getResource(), key);
		if (null != parentResource) {
			final Iterator<Resource> specChildrens = parentResource.listChildren();
			Resource current = null;
			while (specChildrens.hasNext()) {
				current = specChildrens.next();
				Map<String, String> items = new LinkedHashMap<String, String>();
				if (current != null) {
					final ValueMap props = current.getValueMap();
					for (String propKey : props.keySet()) {
						if (null != props.get(propKey)) {
							items.put(propKey, props.get(propKey).toString());
						}
					}
					items.put("nodeName", current.getName());
					tabList.add(items);
				}
			}
		}
		return tabList;
	}

	public static List<ValueMap> getValues(SlingHttpServletRequest slingrequest, String dialogField) {
		final Resource currentResource = getMultiCompositeFieldResource(slingrequest, dialogField);
		return getMultiCompositeFieldChildren(currentResource);
	}	


	private static Resource getMultiCompositeFieldResource(SlingHttpServletRequest slingRequest, String dialogField) {
		Page currentPage = null;
		Design currentDesign = null;
		Style currentStyle = null;
		final ComponentContext componentContext = WCMUtils.getComponentContext(slingRequest);
		if (null != componentContext) {
			currentPage = componentContext.getPage();
		}

		if (null != currentPage) {
			final Designer designer = slingRequest.getResourceResolver().adaptTo(Designer.class);
			currentDesign = designer.getDesign(currentPage);
		}

		if ((null != currentDesign) && (null != componentContext)) {
			currentStyle = currentDesign.getStyle(componentContext.getCell());
		}

		final Resource currentResource = slingRequest.getResource();
		Resource resourceMCF = null;
		if (null != currentResource) {
			resourceMCF = currentResource.getChild(dialogField);
			if (null != resourceMCF) {
				return resourceMCF;
			}

		}

		if (null != currentStyle) {
			final Resource currentStyleResource = currentStyle.getDefiningResource(dialogField);
			if (null != currentStyleResource) {
				resourceMCF = currentStyleResource.getChild(dialogField);
			}
		}

		return resourceMCF;
	}


	public static List<ValueMap> getMultiCompositeFieldChildren(Resource multiCompositeField) {
		final List<ValueMap> childList = new ArrayList<ValueMap>();

		if (null != multiCompositeField) {
			final Iterator<Resource> mcfChildren = multiCompositeField.listChildren();

			Resource current = null;
			while (mcfChildren.hasNext()) {
				current = mcfChildren.next();
				if (current != null) {
					final ValueMap props = current.getValueMap();
					childList.add(props);
				}
			}
		}

		return childList;
	}

	public Map<Integer, List<ValueMap>> getMultiFieldWrapperMethod(){		
		List<ValueMap> valuemap = getMultiField();
		
		Map<Integer, List<ValueMap>> valuemaplist = new HashMap<Integer, List<ValueMap>>();
		List<ValueMap> childList = new ArrayList<ValueMap>();
		
		if (null != valuemap) {
			

			for(int i = 0;i<valuemap.size();i++){
				childList.add(valuemap.get(i));
				if((i+1)%3 == 0) {
					System.out.println(valuemaplist.put(i,childList));
					childList = new ArrayList<ValueMap>();
				}
			}	
		}		
		System.out.println(valuemaplist);
		return valuemaplist;
	}

}
