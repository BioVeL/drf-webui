package org.bgbm.biovel.drf.client.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
   Data object to hold name information and links to associated names (if any).
   Used for all kinds of names (input, accepted, synonyms, etc)
*/
public class SciName {
    public String name = "";
    public boolean exclude = false;

    public Map<String, SciName> accNameMap;
    public Map<String, List<SciName>> synMap;
    public Map<String, List<SciName>> otherNamesMap;
    
    public Map<String, String> classificationMap;

    private Source source;
    private Scrutiny scrutiny;
    
    public SciName() {
	accNameMap = new HashMap<String, SciName>();
	synMap = new HashMap<String, List<SciName>>();
        otherNamesMap = new HashMap<String, List<SciName>>();
        classificationMap = new HashMap<String, String>();
        
        source = new Source("","","","");
        scrutiny = new Scrutiny("","");
    }

    @Override
	public String toString() {
	return name;
    }

    public void setAccName(String checklist, SciName accName) {
	accNameMap.put(checklist, accName);
    }

    public void addSynName(String checklist, SciName synName) {

	if(synMap.isEmpty() || !synMap.containsKey(checklist)) {
	    List<SciName> synList = new ArrayList<SciName>();
	    synList.add(synName);
	    synMap.put(checklist, synList);
	} else {	    
	    List synList = synMap.get(checklist);
	    if(synList == null) {
                System.out.println("Error : Should not happen!! Canonical part " + accNameMap.get(checklist) + " is probably a homonym for checklist "  + checklist);
            } else {
		synList.add(synName);
	    }
	}
    }

    public void setNullSynList(String checklist) {
	synMap.put(checklist, null);
    }
    
    public void addOtherName(String checklist, SciName otherName) {
	if(otherNamesMap.isEmpty() || !otherNamesMap.containsKey(checklist)) {
	    List<SciName> otherNamesList = new ArrayList<SciName>();
	    otherNamesList.add(otherName);
	    otherNamesMap.put(checklist, otherNamesList);
	} else {
	    List otherNamesList = otherNamesMap.get(checklist);
	    otherNamesList.add(otherName);
	}
    }
    
    public void addClassification(String rank, String value) {
        classificationMap.put(rank,value);
    }

    public Map<String, SciName> getAccNameMap() {
	return accNameMap;
    }
    
    public Map<String, List<SciName>> getSynMap() {
	return synMap;
    }

    public Map<String, List<SciName>> getOtherNamesMap() {
	return otherNamesMap;
    }

    public boolean isAccNameEmpty() {
	return accNameMap.isEmpty();
    }
    

    
    public boolean isOtherNameEmpty() {
	return otherNamesMap.isEmpty();
    }
    
    public void setSource(Source source) {
        this.source = source;
    }
    
    public Source getSource() {
        return this.source;
    }
    
    public void setScrutiny(Scrutiny scrutiny) {
        this.scrutiny = scrutiny;
    }
    
    public Scrutiny getScrutiny() {
        return this.scrutiny;
    }
    
    public static List buildUniqueNameList(List<SciName> nameList) {
        Set uniqueNameSet = new HashSet();
        Iterator itr = nameList.iterator();

        while (itr.hasNext()) {
            SciName inputName = (SciName) itr.next();
            if (inputName != null && !inputName.name.equals("") && !inputName.exclude) {
                uniqueNameSet.add(inputName.name);
            }

            Iterator<Map.Entry<String, SciName>> anentries = inputName.getAccNameMap().entrySet().iterator();
            while (anentries.hasNext()) {
                Map.Entry entry = (Map.Entry) anentries.next();

                SciName accName = (SciName) entry.getValue();
                if (accName != null && !accName.name.equals("") && !accName.exclude) {
                    uniqueNameSet.add(accName.name);
                }
            }

            Iterator<Map.Entry<String, List<SciName>>> snentries = inputName.getSynMap().entrySet().iterator();
            while (snentries.hasNext()) {
                Map.Entry entry = (Map.Entry) snentries.next();
                List synList = (List) entry.getValue();
                if (synList != null && synList.size() > 0) {
                    Iterator synListItr = synList.iterator();
                    while (synListItr.hasNext()) {
                        SciName synName = (SciName) synListItr.next();
                        if (synName != null && !synName.name.equals("") && !synName.exclude) {
                            uniqueNameSet.add(synName.name);
                        }
                    }
                }
            }
        }

        return new ArrayList(uniqueNameSet);       
    }
    
    public static void filterName(List<SciName> nameList, String name, boolean exclude) {
        Set uniqueNameSet = new HashSet();
        Iterator itr = nameList.iterator();

        while (itr.hasNext()) {
            SciName inputName = (SciName) itr.next();
            if (inputName != null && inputName.name.equals(name)) {
                inputName.exclude = exclude;
            }

            Iterator<Map.Entry<String, SciName>> anentries = inputName.getAccNameMap().entrySet().iterator();
            while (anentries.hasNext()) {
                Map.Entry entry = (Map.Entry) anentries.next();

                SciName accName = (SciName) entry.getValue();
                if (accName != null && accName.name.equals(name)) {
                	 accName.exclude = exclude;
                }
            }

            Iterator<Map.Entry<String, List<SciName>>> snentries = inputName.getSynMap().entrySet().iterator();
            while (snentries.hasNext()) {
                Map.Entry entry = (Map.Entry) snentries.next();
                List synList = (List) entry.getValue();
                if (synList != null && synList.size() > 0) {
                    Iterator synListItr = synList.iterator();
                    while (synListItr.hasNext()) {
                        SciName synName = (SciName) synListItr.next();
                        if (synName != null && synName.name.equals(name)) {
                        	synName.exclude = exclude;
                        }
                    }
                }
            }
        }      
    }
    
    
    public class Source {
        public String name = "";
        public String url = "";
        public String datasetID = "";
        public String datasetName = "";
        
        public Source(String name, String url, String datasetID, String datasetName) {
            this.name = name;
            this.url = url;
            this.datasetID = datasetID;
            this.datasetName = datasetName;
        }
    }
    
    public class Scrutiny {
        
        public String accordingTo = "";
        public String modified = "";
        
        public Scrutiny(String accordingTo, String modified) {
            this.accordingTo = accordingTo;
            this.modified = modified;
        }
    }
    
    
}

