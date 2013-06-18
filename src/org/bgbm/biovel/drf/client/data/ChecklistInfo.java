package org.bgbm.biovel.drf.client.data;

/**
*
* @author cmathew
*/
public class ChecklistInfo {

   private String checklist;
   private String checklist_url;
   private String checklist_version;
   private String checklist_citation;
   public ChecklistInfo(String checklist,String checklist_url,String checklist_verison,String checklist_citation) {
       this.checklist = checklist;
       this.checklist_url = checklist_url;
       this.checklist_version = checklist_verison;
       this.checklist_citation = checklist_citation;
   }

   /**
    * @return the checklist
    */
   public String getChecklist() {
       return checklist;
   }

   /**
    * @param checklist the checklist to set
    */
   public void setChecklist(String checklist) {
       this.checklist = checklist;
   }

   /**
    * @return the checklist_version
    */
   public String getChecklist_version() {
       return checklist_version;
   }

   /**
    * @param checklist_version the checklist_version to set
    */
   public void setChecklist_version(String checklist_version) {
       this.checklist_version = checklist_version;
   }

   /**
    * @return the checklist_citation
    */
   public String getChecklist_citation() {
       return checklist_citation;
   }

   /**
    * @param checklist_citation the checklist_citation to set
    */
   public void setChecklist_citation(String checklist_citation) {
       this.checklist_citation = checklist_citation;
   }

   /**
    * @return the checklist_url
    */
   public String getChecklist_url() {
       return checklist_url;
   }

   /**
    * @param checklist_url the checklist_url to set
    */
   public void setChecklist_url(String checklist_url) {
       this.checklist_url = checklist_url;
   }
}
