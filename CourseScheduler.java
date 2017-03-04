/**
 **************************************************************************
 * @author: Sanil Rijal
 *
 * Date: 4/20/2016
 *
 * Description: This CourseScheduler class determines the minimumnumber 
 * of time slots  steps and required schedule classes so that no classes 
 * conflict within the given criterion i.e when they are taught by the 
 * same instructor, when they are scheduled in the same classroom and 
 * when they are in the same discipline and are both numbered 3xxx or 4xxx.
 * The class uses graph-coloring backtracking algorithm .
 ***************************************************************************
 **/
import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class CourseScheduler {
	//Data Fields
   /** The file from which input is to be taken.*/
   File file;
   /** The given number of classes to be processed.*/
   int testCases;
   /** An adjancency matrix created using 2D-array to check the given criterion.*/
   boolean W[][];
   /** An array to store the course that fall in a single timeslot.*/
   int vcolor[];
   /** The minimum number of timeslots required.*/
   int m;
	/** The minimum number of color for m_coloring.*/
   int color;
   /** An array to store the created objects of the courses.*/

   Class currentData[];
   /** A boolean variable initialized false for m_coloring.*/
   boolean check=false;
   
   /** The m_coloring method assigns timeslots for the courselist.
     */
   boolean m_coloring (int i)
   { 
      if (promising (i))
      {
         if (i == testCases-1)
         {
            check=true;
            return true;
         }
         else {
            for (color = 1; color <= testCases; color++){
               if(check==true){
                  break;
               }                                      // Try every
               vcolor [i + 1] = color;            // color for
               m_coloring (i + 1);                // next vertex.
            }
         }
      }
      return false;
   }
	
	/**This method check whether the is promising or not
    * @return It returns boolean value whether it is promising or not.  */
   boolean promising (int i)
   {
      int j;
      boolean s;
   
      s = true;
      j = 0;
      while (j<i && s){                           // Check if an
         if (W[i][j] && vcolor[i] == vcolor[j]) { // adjacent vertex
            s = false;                            // is already
         }
         j++;                                     // this color.
      }
      return s;
   }
   
	/** A constructor to initialize and read the input from a file and arrays used to store
     * the data.
     * @param filename The input file to be processed. */
   public CourseScheduler(String filename) {
   	
      File file = new File(filename);
      String lineData[];
      Class temp;
   	
      try {
      
         Scanner scanner = new Scanner(file);
      
           
         if(scanner.hasNext())
            testCases = Integer.parseInt(scanner.next());
         scanner.nextLine();
         vcolor = new int[testCases];
         for (int i = 0; i < vcolor.length; i++) {
            vcolor[i] = 0;
         }
           
           
         W=new boolean[testCases][testCases];
         for (int i = 0; i < W.length; i++) {
            for (int j = 0; j < W.length; j++) {
               W[i][j] = false;
            }
         	
         }
         currentData= new Class[testCases];
         String line;
           
         for(int i=0; i<testCases; i++) {
            line = scanner.nextLine();
            while(line.length()==0)
               line = scanner.nextLine();
            lineData = line.split(",");
            temp = new Class(lineData[0], lineData[1], lineData[2], lineData[3], lineData[4]);
            currentData[i] = temp;
         }
           
         for(int i=0; i<currentData.length;i++)
            for (int j = 0; j < currentData.length; j++) {
               if(currentData[i].INSTRUCTOR.equals(currentData[j].INSTRUCTOR))
                  W[i][j] = true;
               if ((currentData[i].BUILDING.equals(currentData[j].BUILDING)) && (currentData[i].ROOM.equals(currentData[j].ROOM)))
                  W[i][j] = true;
               if (currentData[i].DEPT.equals(currentData[j].DEPT) &&
                (Integer.parseInt(currentData[i].NUMBER)/1000 == 3 || Integer.parseInt(currentData[i].NUMBER)/1000 == 4) 
                && (Integer.parseInt(currentData[j].NUMBER)/1000 == 3 || Integer.parseInt(currentData[j].NUMBER)/1000 == 4))
                  W[i][j] = true;
            
            }
           
           
         scanner.close();
      } 
      catch (FileNotFoundException e) {
         e.printStackTrace();
      }
   	
   	
   }
	/** A method to find the minimum number of timeslots to schedule the given list of classes.
     * @return The minimum number of timeslots required. */
   public int minSlots() {
      m=1;
      
      vcolor[0] = 1;
         
      m_coloring(0);
         
      for (int i = 0; i < vcolor.length; i++) {
         if (vcolor[i] > m) {
            m = vcolor[i];
         }
      }
        
      return m;
   }
	/** The method returns the list of courses scheduled in different timeslots in a proper
     * readable manner. 
     * @return The minimum number of timeslots and the list of classes in the slots.*/
   public String getSchedule() {
      String output = "";
      for(int j=1; j<=m; j++) {
         output+="\nTimeSlot #" + j + ":\n";
         for (int i=0; i<vcolor.length;i++){
            if(vcolor[i] == j)
               output += currentData[i].getDEPT() + " "+ currentData[i].getNUMBER() +
                  " "+ currentData[i].getBUILDING()+" "+currentData[i].getROOM()+" "+
                  currentData[i].getINSTRUCTOR()+"\n";
            
         }
      }
      
      
      return output;
   }


/** A class to read, initialize and store the courses and its attributes.*/
   public class Class {
      /** A string to initialize the academic discipline of the course.  */
      String DEPT;
      /** A string to initialize the level of the course.  */
      String NUMBER;
      /** A string to initialize the builiding where the course is taught.  */
      String BUILDING;
      /** A string to initialize the room number of the classroom.  */
      String ROOM;
      /** A string to initialize the name of the instructor of the course.  */
      String INSTRUCTOR;
   	
     
   /** A method to return the academic discipline of the course.  */
      public String getDEPT() {
         return DEPT;
      }
   /** A method to return the level of the course.
     * @return The level of the course.
     */
      public String getNUMBER() {
         return NUMBER;
      }
   /** A method to return the name of building where the course is taught.
     * @return The name of building where the course is taught.  
     */  
      public String getBUILDING() {
         return BUILDING;
      }
   /** A method to return the room number of the classroom of the course.
     * @return The room number of the classroom of the course.
     */
      public String getROOM() {
         return ROOM;
      }
   /** A method to return the name of the instructor of the course.
     * @return the name of the instructor of the course.  */
      public String getINSTRUCTOR() {
         return INSTRUCTOR;
      }
   /** A constructor to initialize the academic discipline, level, building, room number
     * and the instructor of the course.
     * @param dEPT The academic discipline of the course.
     * @param nUMBER The level of the course.
     * @param bUILDING The building where the course is taught.
     * @param rOOM The room number of the classroom.
     * @param iNSTRUCTOR The name of the instructor.*/
      public Class(String dEPT, String nUMBER, String bUILDING, String rOOM, String iNSTRUCTOR) {
         super();
         DEPT = dEPT;
         NUMBER = nUMBER;
         BUILDING = bUILDING;
         ROOM = rOOM;
         INSTRUCTOR = iNSTRUCTOR;
      }
   }
}
