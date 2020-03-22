import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

/*
 * This will be the main class for lab 4's sorting algorithms
 * It will digest the text files, and then perform the specified
 * sort.
 */
public class Sort 
{
  public static void main(String[] args)
  {
    
    //Initiates user input as the scanner, allowing for user input
    Scanner input = new Scanner(System.in);
    
    //asks the user for important information
    System.out.println("Input file location: ");
    String inputLocation = input.nextLine();
    
    System.out.println("Merge sort? (0 - No, 1 - Yes): ");
    int chooseMergeSort = input.nextInt();
    System.out.println("Stopping cases: ");
    int stoppingCase = input.nextInt();
    System.out.println("Insertion Sort within Quick Sort? (0 - No, 1 - Yes): ");
    int insertionSort = input.nextInt();
    System.out.println("Median of three? (0 - No, 1 - Yes): ");
    int medianofthree = input.nextInt();
    
    System.out.println("Output file location: ");
    String outputLocation = input.nextLine();
    
    //ingests thef ile and gets it ready to use
    ArrayList<Integer> linkedList = readFile(inputLocation);
    ArrayList<Integer> sortedArray = new ArrayList<Integer>();
    
    //determines if a merge sort or quick sort should be run
    if(chooseMergeSort == 1)
    {
      sortedArray = mergeSort(linkedList);
    }
    else
    {
      sortedArray = quickSort(linkedList, stoppingCase, insertionSort, medianofthree);
    }
    
    toFile(sortedArray, outputLocation);
    
  }
  
  //this method will perform a quicksort on the array list
  public static ArrayList<Integer> quickSort(ArrayList<Integer> linkedList, int stoppingCase, int insertionSort, int medianofthree)
  {
    Date date = new Date();
    long start = date.getTime();
    //creating a temp array and a final array to store the information
    ArrayList<ArrayList<Integer>> finalArray = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> workingArray = new ArrayList<ArrayList<Integer>>();
    
    //placing the full linked list into the working array
    workingArray.add(linkedList);
    
    int pivot;
    
    //setting starting pivot, bottom, top and other variables
    if(medianofthree == 1)
    {
      int pivot1 = (int) workingArray.get(0).get(0);
      int pivot2 = (int) workingArray.get(0).get(0);
      int pivot3 = (int) workingArray.get(0).get(0);
      ArrayList<Integer> temp = new ArrayList<Integer>();
      temp.add(pivot1);
      temp.add(pivot2);
      temp.add(pivot3);
      temp = insertionSort(temp);
      pivot = temp.get(1);
    }
    else
    {
      pivot = (int) workingArray.get(0).get(0);
    }
    int bottom = 0;
    int top = workingArray.get(0).size()-1;
    int down = 1;
    
    //these arrays will hold the sorted buckets
    ArrayList<Integer> lower = new ArrayList<Integer>();
    ArrayList<Integer> pivotArray = new ArrayList<Integer>();
    ArrayList<Integer> upper = new ArrayList<Integer>();
    
    //runs until working array is empty
    while(workingArray.isEmpty() == false)
    {
      //upper bound
      if(down == 1)
      {
        //if a swap needs to happen
        if((int) workingArray.get(0).get(top) < pivot)
        {
          int temp = workingArray.get(0).get(top);
          workingArray.get(0).set(bottom, temp);
          bottom++;
          down = 0;
        }
        //if a swap isn't necessary
        else
        {
          top--;
        }
      }
      //downward bound
      else if (down == 0)
      {
        //if a swap needs to happen
        if((int) workingArray.get(0).get(bottom) > pivot)
        {
          int temp = workingArray.get(0).get(bottom);
          workingArray.get(0).set(top, temp);
          top--;
          down = 1;
        }
        //if a swap isn't necessary
        else
        {
          bottom++;
        }
      }
      
      //if the array is entirely sorted
      if(bottom == top)
      {
        //puts the pivot back in place
        workingArray.get(0).set((int)top, pivot);
        
        //fills the temperary arrays with the indexes that were sorted
        for(int index = 0 ; index < workingArray.get(0).size() ; index++)
        {
          if((int) workingArray.get(0).get(index) < pivot)
          {
            lower.add(workingArray.get(0).get(index));
          }
          else if((int) workingArray.get(0).get(index) == pivot)
          {
            pivotArray.add(workingArray.get(0).get(index));
          }
          else
          {
            upper.add(workingArray.get(0).get(index));
          }
        }
        
        //a temp array that will be used to rebuild the working array
        ArrayList<ArrayList<Integer>> tempArray = new ArrayList<ArrayList<Integer>>();
        
        //fills the temp array with the extra stuff not worked on yet
        for(int index = 1 ; index < workingArray.size() ; index++)
        {
          tempArray.add(workingArray.get(index));
        }
        workingArray = tempArray;
        
        //only inserts information if there is information to insert
        if(upper.isEmpty() == false)
        {
          workingArray.add(0, upper);
        }
        if(pivotArray.isEmpty() == false)
        {
          workingArray.add(0, pivotArray);
        }
        if(lower.isEmpty() == false)
        {
          workingArray.add(0, lower);
        }
        
        //used to fill the final array based on the stopping case
        while(workingArray.get(0).size() <= stoppingCase)
        {
          ArrayList<Integer> temp = new ArrayList<Integer>();
          temp = workingArray.get(0);
          finalArray.add(temp);
          workingArray.remove(0);
          if(workingArray.isEmpty() == true)
          {
            break;
          }
        }
        
        //ensures that the working array isn't finished
        if(workingArray.isEmpty() == false)
        {
          if(workingArray.get(0).size() != 0)
          {
            if(medianofthree == 1 && workingArray.get(0).size() > 2)
            {
              int pivot1 = (int) workingArray.get(0).get(0);
              int pivot2 = (int) workingArray.get(0).get(0);
              int pivot3 = (int) workingArray.get(0).get(0);
              ArrayList<Integer> temp = new ArrayList<Integer>();
              temp.add(pivot1);
              temp.add(pivot2);
              temp.add(pivot3);
              temp = insertionSort(temp);
              pivot = temp.get(1);
            }
            else
            {
              pivot = (int) workingArray.get(0).get(0);
            }
            bottom = 0;
            top = workingArray.get(0).size()-1;
            down = 1;
          }
        }
        
        //breaks the loop if the array is finished
        else
        {
          break;
        }
        
        //refreshes some information
        ArrayList<Integer> holderLower = new ArrayList<Integer>();
        lower = holderLower;
        ArrayList<Integer> holderPivot = new ArrayList<Integer>();
        pivotArray = holderPivot;
        ArrayList<Integer> holderUpper = new ArrayList<Integer>();
        upper = holderUpper;
      }
    }
    
    //turns the nested array into a one dimentional array
    ArrayList<Integer> array = toArrayList(finalArray);
    
    Date date2 = new Date();
    long end = date2.getTime();
    long elapsed = end-start;
    System.out.println("Time elapsed: "+elapsed);
    //performs insertion sort if asked
    if(insertionSort == 1)
    {
      array = insertionSort(array);
      return array;
    }
    
    //if no insertion sort is required
    else
    {
      return array;
    }
  }
  
  //this method will convert a nested array list to a one dimentional array list
  public static ArrayList<Integer> toArrayList(ArrayList<ArrayList<Integer>> array)
  {
    //creates a holder array
    ArrayList<Integer> holderArray = new ArrayList<Integer>();
    
    //cycles through each array within the array list
    for(int arrayIndex = 0 ; arrayIndex < array.size() ; arrayIndex++)
    {
      //cycles through each index in the given array
      for(int index = 0 ; index < array.get(arrayIndex).size() ; index++)
      {
        holderArray.add(array.get(arrayIndex).get(index));
      }
    }
    return holderArray;
  }
  
  //this method performs an insertion sort
  public static ArrayList<Integer> insertionSort(ArrayList<Integer> array)
  {
    //starts at the beginning
    int index = 0;
    //will run until it hits the end
    while(index < array.size())
    {
      int current = index;
      
      //runs until it is in the correct index
      while(current > 0 && array.get(current) <= array.get(current-1))
      {
        int temp = array.get(current);
        int temp2 = array.get(current-1);
        
        array.set(current, temp2);
        array.set(current-1, temp);
        current = current-1;
      }
      index = index+1;
    }
    return array;
  }
  
  //this method is called to perform a merge sort
  public static ArrayList<Integer> mergeSort(ArrayList<Integer> linkedList)
  {
    //datetime to capture the runtime in ms
    Date date = new Date();
    long start = date.getTime();
    
    //creates a final array that will be used to store the sorted information
    ArrayList<ArrayList<Integer>> finalArray = new ArrayList<ArrayList<Integer>>();
    int arrayIndex = 0;

    //holder array
    ArrayList<Integer> holderArray = new ArrayList<Integer>();
    
    //runs through the initial pass to create arrays of already sorted data
    for(int index = 0 ; index < linkedList.size() ; index++)
    {
      //picks up the first pass and passes after a reset
      if(arrayIndex == 0 || arrayIndex == -1)
      {
        //passes after a reset
        if(arrayIndex == -1)
        {
          //set index to 1 so that it is back on track
          arrayIndex = 1;
          if((int) holderArray.get(arrayIndex-1) <= (int) linkedList.get(index))
          {
            //add the current value into the current list
            holderArray.add(linkedList.get(index));
            arrayIndex++;
          }
          else
          {
            //first pass initializes some variables
            arrayIndex = 0;
            finalArray.add(holderArray);
            ArrayList<Integer> temp = new ArrayList<Integer>();
            holderArray = temp;
            holderArray.add(linkedList.get(index));
            arrayIndex = -1;
          }
        }
        else
        {
          holderArray.add(linkedList.get(index));
          arrayIndex++; 
        }
      }
      
      //if it is not the first pass or a pass after a reset
      else
      {
        //check to see if the next value is larger than the current value
        if((int) holderArray.get(arrayIndex-1) <= (int) linkedList.get(index))
        {
          //adds it if so
          holderArray.add(linkedList.get(index));
          arrayIndex++;
        }
        
        //if it is not, then the whole thing is reset, with the current array being dumped into
        //a holder array used to stockpile all of the parts
        else
        {
          arrayIndex = 0;
          finalArray.add(holderArray);
          ArrayList<Integer> temp = new ArrayList<Integer>();
          holderArray = temp;
          holderArray.add(linkedList.get(index));
          arrayIndex = -1;
        }
      }
      
      //should only be called if the array is already sorted
      if(finalArray.isEmpty() == true)
      {
        finalArray.add(holderArray);
      }
    }
    
    //initializes some variables used later on
    int workingIndex = 0;
    ArrayList<ArrayList<Integer>> tempFinalArray = new ArrayList<ArrayList<Integer>>();
    
    while(true)
    {
      //as long as there are files to be worked on   
      if(workingIndex < finalArray.size()-1)
      {
        //performs a merge
        tempFinalArray.add(merge(finalArray.get(workingIndex), finalArray.get(workingIndex+1)));
        workingIndex++;
        workingIndex++;
      }
      //if the last file is being worked on
      else if (workingIndex == finalArray.size()-1)
      {
        tempFinalArray.add(finalArray.get(workingIndex));
        workingIndex++;
      }
      //creates a temp final array before returning the last final array
      else
      {
        ArrayList<ArrayList<Integer>> temp2 = new ArrayList<ArrayList<Integer>>();
        finalArray = temp2;
        finalArray = tempFinalArray;
        tempFinalArray = temp2;
        workingIndex = 0;
      }
      
      //if all information is now in the final array
      if(finalArray.size() == 1)
      {
        break;
      }
    }
    //transforms the two dimentional array into a one dimentional array
    ArrayList<Integer> array = toArrayList(finalArray);
    
    //ends the run
    Date date2 = new Date();
    long end = date2.getTime();
    long elapsed = end-start;
    System.out.println("Time elapsed: "+elapsed);
    
    return array;
  }

  //this method will perform a merge of two seperate arrays
  public static ArrayList<Integer> merge(ArrayList<Integer> array1, ArrayList<Integer> array2)
  {
    //creates a final merged array that will be returned
    ArrayList<Integer> mergedArray = new ArrayList<Integer>();
    
    //starting indexes
    int index1 = 0;
    int index2 = 0;
    
    //runs until both arrays are fully cycled throguh
    while(index1 != array1.size() || index2 != array2.size())
    {
      //if array 1 is fully passed through
      if(index1 == array1.size())
      {       
        mergedArray.add(array2.get(index2));
        index2++;
      }
      //if array 2 is fully passed through
      else if(index2 == array2.size())
      {
        mergedArray.add(array1.get(index1));
        index1++;
      }
      
      //else see if which array's current index should be placed into the merged array
      else if((int) array1.get(index1) < (int) array2.get(index2))
      {
        mergedArray.add(array1.get(index1));
        index1++;
      }
      else
      {
        mergedArray.add(array2.get(index2));
        index2++;
      }
    }
    return mergedArray;
  }
  
  //writes the array to a file location
  public static void toFile(ArrayList<Integer> array, String fileLocation)
  {
    try
    {
    //starts writing
    PrintWriter writer = new PrintWriter(fileLocation, "UTF-8");
    
    //loops through and writes every index on a new line
    for(int index = 0 ; index < array.size() ; index ++)
    {
      writer.println(array.get(index));
    }
    //closes the file
    writer.close();
    }
    catch(Exception exceptions)
    {
      System.out.println("Warning! An error occured during file write");
    }   
  }
  
  //this method takes the file information and breaks it apart by matrix
  public static ArrayList<Integer> readFile(String filelocation)
  {
    ArrayList<Integer> numberList = new ArrayList<Integer>();
    //input stream
    FileReader inputStream = null;
    try 
    {
      //designates the file location
      inputStream = new FileReader(filelocation);
      
      int c;
      int first = 1;
      String holderString = "";
      //reads the file character by character
      while ((c = inputStream.read()) != -1) 
      { 
        //changes the character to an integer
        char character = (char) c;
        int characterinteger = Character.getNumericValue(character);
        if(characterinteger > -1)
        {
          holderString = holderString+character;
        }
        else
        {
          if(holderString != "")
          {
            numberList.add(Integer.parseInt(holderString));
          }
          holderString = "";
        }
      }
      
      
      //closes the read
      inputStream.close();
    } 
    catch(Exception except)
    {
      //lets me know an error occurred
      System.out.println("An error occurred.");
    }
    
    return numberList;
  }
}
