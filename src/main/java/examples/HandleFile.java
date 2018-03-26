package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HandleFile {

	public void run() {
		/*
		List<String> dataList=new ArrayList<String>();
        dataList.add("Developer,File,EventType,CommandID,TriggeredTime,Duration,Runs,Success,Failed,Error,Ignored,Access,QuickFixTimes,RefactorTimes");
        CSVUtils.exportCsv(new File("TestsStatus.csv"), dataList);
		*/
		
		List<String> list=CSVUtils.importCsv(new File("Unlinked_RefactorsAndQuickfixesBetweenTests.csv"));
		int quickFixNum=0;
		int refactorNum=0;
		String document=new String("");
		String userZip=new String("");
		String access=new String();

		for(int i=1;i<list.size();i++) {

			String[] temp=list.get(i).split(",");
			if(temp[0].equals(userZip)) {
				if(temp[2].equals("CommandEvent")) {
					if(temp[3].contains("QuickFixes"))
						quickFixNum++;
					else
						refactorNum++;
				}
				else if(temp[2].equals("TestRunEvent")&&(Integer.parseInt(temp[6]))!=0&&refactorNum!=0) {
					List<String> outList=new ArrayList<String>();
					access=Double.toString((double)(Integer.parseInt(temp[7]))/(double)(Integer.parseInt(temp[6])));
					outList.add(access+","+Integer.toString(quickFixNum)+","+Integer.toString(refactorNum));
					CSVUtils.exportCsv(new File("Unlinked_TestsStatus.csv"), outList);
					
					quickFixNum=0;
					refactorNum=0;
				}
			}
			else {
				quickFixNum=0;
				refactorNum=0;
				document=new String(temp[1]);
				userZip=new String(temp[0]);
				
				if(temp[2].equals("CommandEvent")) {
					if(temp[3].contains("QuickFixes"))
						quickFixNum++;
					else
						refactorNum++;
				}
				else if(temp[2].equals("TestRunEvent")&&(Integer.parseInt(temp[6]))!=0&&refactorNum!=0) {
					List<String> outList=new ArrayList<String>();
					access=Double.toString((double)(Integer.parseInt(temp[7]))/(double)(Integer.parseInt(temp[6])));
					outList.add(access+","+Integer.toString(quickFixNum)+","+Integer.toString(refactorNum));
					CSVUtils.exportCsv(new File("Unlinked_TestsStatus.csv"), outList);
					
					quickFixNum=0;
					refactorNum=0;
				}
			}
		}
	}
	
	
	
	
	public void getPassRateWithLastPassRate() {
		
		List<String> list=CSVUtils.importCsv(new File("TestEventList.csv"));

		for(int i=1;i<list.size();i++) {

			String[] temp1=list.get(i).split(",");
			for(int j=i-1;j>=1;j--) {
				
				String[] temp2=list.get(j).split(",");
				
				if(!temp2[0].equals(temp1[0]))
					break;
				else if(isRelated(temp1[1],temp2[1],3)) {
					
					List<String> outList=new ArrayList<String>();
					
					String lastPassRate;
					if(temp2[5].equals("0"))
						lastPassRate="0";
					else
						lastPassRate=Double.toString((double)(Integer.parseInt(temp2[6]))/(double)(Integer.parseInt(temp2[5])));
					
					String passRate;
					if(temp1[5].equals("0"))
						passRate="0";
					else
						passRate=Double.toString((double)(Integer.parseInt(temp1[6]))/(double)(Integer.parseInt(temp1[5])));
					
					outList.add(passRate+","+lastPassRate);
					CSVUtils.exportCsv(new File("LastTestPassRate.csv"), outList);
					break;
				}
			}
		}
	}
	
	
	public void getPassRateWithRefactors() { //统计test是否通过以及两次test间是否有refactor操作
		List<String> list=CSVUtils.importCsv(new File("RefactorsAndQuickfixesBetweenTests.csv"));

		for(int i=1;i<list.size();i++) {

			String[] temp1=list.get(i).split(",");
			
			if(temp1[2].equals("TestRunEvent")) {
				for(int j=i-1;j>=1;j--) {
					
					String[] temp2=list.get(j).split(",");
					
					if(!temp2[0].equals(temp1[0])) {
						
						List<String> outList=new ArrayList<String>();
						String isPassed;
						if(temp1[6].equals("0"))
							isPassed="0";
						else
							isPassed=Double.toString((double)(Integer.parseInt(temp1[7]))/(double)(Integer.parseInt(temp1[6])));
						outList.add(isPassed+","+"0");
						CSVUtils.exportCsv(new File("PassRateWithRefactors.csv"), outList);
						break;
					}
					else if(isRelated(temp1[1],temp2[1],3)) {
						if(temp2[2].equals("TestRunEvent")) {
							
							List<String> outList=new ArrayList<String>();
							String isPassed;
							if(temp1[6].equals("0"))
								isPassed="0";
							else
								isPassed=Double.toString((double)(Integer.parseInt(temp1[7]))/(double)(Integer.parseInt(temp1[6])));
							outList.add(isPassed+","+"0");
							CSVUtils.exportCsv(new File("PassRateWithRefactors.csv"), outList);
							break;
						}
						else if(temp2[2].equals("CommandEvent")&&!temp2[3].contains("QuickFixes")){
							/*
							List<String> outList=new ArrayList<String>();
							String isPassed;
							if(temp1[6].equals("0"))
								isPassed="0";
							else
								isPassed=Double.toString((double)(Integer.parseInt(temp1[7]))/(double)(Integer.parseInt(temp1[6])));
							outList.add(isPassed+","+"1");
							CSVUtils.exportCsv(new File("PassRateWithRefactors.csv"), outList);*/
							break;
						}
					}
				}
			}
		}
	}
	
	public void getPassRateWithQuickFixes() { //统计test是否通过以及两次test间是否有QuickFix操作
		List<String> list=CSVUtils.importCsv(new File("RefactorsAndQuickfixesBetweenTests.csv"));

		for(int i=1;i<list.size();i++) {

			String[] temp1=list.get(i).split(",");
			
			if(temp1[2].equals("TestRunEvent")) {
				for(int j=i-1;j>=1;j--) {
					
					String[] temp2=list.get(j).split(",");
					
					if(!temp2[0].equals(temp1[0])) {
						/*
						List<String> outList=new ArrayList<String>();
						String isPassed;
						if(temp1[6].equals("0"))
							isPassed="0";
						else
							isPassed=Double.toString((double)(Integer.parseInt(temp1[7]))/(double)(Integer.parseInt(temp1[6])));
						outList.add(isPassed+","+"0");
						CSVUtils.exportCsv(new File("PassRateWithQuickFixes.csv"), outList);*/
						break;
					}
					else if(isRelated(temp1[1],temp2[1],3)) {
						if(temp2[2].equals("TestRunEvent")) {
							/*
							List<String> outList=new ArrayList<String>();
							String isPassed;
							if(temp1[6].equals("0"))
								isPassed="0";
							else
								isPassed=Double.toString((double)(Integer.parseInt(temp1[7]))/(double)(Integer.parseInt(temp1[6])));
							outList.add(isPassed+","+"0");
							CSVUtils.exportCsv(new File("PassRateWithQuickFixes.csv"), outList);*/
							break;
						}
						else if(temp2[2].equals("CommandEvent")&&temp2[3].contains("QuickFixes")){

							List<String> outList=new ArrayList<String>();
							String isPassed;
							if(temp1[6].equals("0"))
								isPassed="0";
							else
								isPassed=Double.toString((double)(Integer.parseInt(temp1[7]))/(double)(Integer.parseInt(temp1[6])));
							outList.add(isPassed+","+"1");
							CSVUtils.exportCsv(new File("PassRateWithQuickFixes.csv"), outList);
							break;
						}
					}
				}
			}
		}
	}
	
	public void getPassRateWithInterval() {
		List<String> list=CSVUtils.importCsv(new File("TestEventList.csv"));

		for(int i=1;i<list.size();i++) {

			String[] temp1=list.get(i).split(",");
			for(int j=i-1;j>=1;j--) {
				
				String[] temp2=list.get(j).split(",");
				
				if(!temp2[0].equals(temp1[0]))
					break;
				else if(isRelated(temp1[1],temp2[1],3)) {
					
					List<String> outList=new ArrayList<String>();
					
					String testInterval = new String();
					testInterval=Long.toString(Interval.getInterval(temp1[3], temp2[3]));
					
					String passRate;
					if(temp1[5].equals("0"))
						passRate="0";
					else
						passRate=Double.toString((double)(Integer.parseInt(temp1[6]))/(double)(Integer.parseInt(temp1[5])));
					
					outList.add(passRate+","+testInterval);
					CSVUtils.exportCsv(new File("PassRateWithInterval.csv"), outList);
					break;
				}
			}
		}
	}
	
	public void getPassRateWithLastCaseNum() {
		List<String> list=CSVUtils.importCsv(new File("TestEventList.csv"));

		for(int i=1;i<list.size();i++) {

			String[] temp1=list.get(i).split(",");
			for(int j=i-1;j>=1;j--) {

				String[] temp2=list.get(j).split(",");

				if(!temp2[0].equals(temp1[0]))
					break;
				else if(isRelated(temp1[1],temp2[1],3)) {

					List<String> outList=new ArrayList<String>();

					String lastCaseNum=temp2[5];

					String passRate;
					if(temp1[5].equals("0"))
						passRate="0";
					else
						passRate=Double.toString((double)(Integer.parseInt(temp1[6]))/(double)(Integer.parseInt(temp1[5])));

					outList.add(passRate+","+lastCaseNum);
					CSVUtils.exportCsv(new File("PassRateWithLastCaseNum.csv"), outList);
					break;
				}
			}
		}
	}

	public void getPassRateWithTestCaseNum(){
		List<String> list=CSVUtils.importCsv(new File("TestEventList.csv"));

		for(int i=1;i<list.size();i++) {

			String[] temp=list.get(i).split(",");
			List<String> outList=new ArrayList<String>();

			String caseNum=temp[5];

			String passRate;
			if(temp[5].equals("0"))
				passRate="0";
			else
				passRate=Double.toString((double)(Integer.parseInt(temp[6]))/(double)(Integer.parseInt(temp[5])));

			outList.add(passRate+","+caseNum);
			CSVUtils.exportCsv(new File("PassRateWithTestCaseNum.csv"), outList);
		}
	}


	public boolean isRelated(String file1, String file2, int principle) {
			if(principle==1) { //不考虑两者的link关系
				return true;
			}
			else if(principle==2) { //两者按照文件名是否相同来link
				return file1.equals(file2);
			}
			else if(principle==3) { //两者按照文件名的相关程度来link
				return CompareFileDirectory.getSimilarity(file1,file2)>=0.5;
			}
			else
				return false;
				
	}
	
	
	
	
	/*
	public void getTests() {
		
		List<String> list=CSVUtils.importCsv(new File("TestEventList.csv"));
		int testNum=0;
		String userZip=new String("");

		for(int i=1;i<list.size();i++) {

			String[] temp=list.get(i).split(",");
			
			if(temp[0].equals(userZip)) {
				testNum++;
				if(i==list.size()-1) {
					List<String> outList=new ArrayList<String>();
					outList.add(userZip+","+Integer.toString(testNum)+","+Long.toString(FileSize.getFileSize(userZip)));
					CSVUtils.exportCsv(new File("TestNumber.csv"), outList);
				}
			}
			else {
				
				if(!userZip.equals("")) {
					List<String> outList=new ArrayList<String>();
					outList.add(userZip+","+Integer.toString(testNum)+","+Long.toString(FileSize.getFileSize(userZip)));
					CSVUtils.exportCsv(new File("TestNumber.csv"), outList);
					
					testNum=1;
					userZip=new String(temp[0]);
				}
				else {
					testNum=1;
					userZip=new String(temp[0]);
				}
				
			}
		}
	}
	*/
	
	/*
	public void getPassRate() {
		
		List<String> list=CSVUtils.importCsv(new File("TestEventList.csv"));

		for(int i=1;i<list.size();i++) {

			String[] temp=list.get(i).split(",");

			if(Integer.parseInt(temp[5])!=0) {
				List<String> outList=new ArrayList<String>();
				outList.add(Double.toString((double)(Integer.parseInt(temp[6]))/(double)(Integer.parseInt(temp[5]))));
				CSVUtils.exportCsv(new File("TestPassRate.csv"), outList);
			}
		}
	}
	*/
	
	/*
	public void getCommands() {
		
		List<String> list=CSVUtils.importCsv(new File("CommandsBetweenTests.csv"));
		int commandNum=0;
		String userZip=new String("");

		for(int i=1;i<list.size();i++) {

			String[] temp=list.get(i).split(",");
			
			if(temp.length<11||!temp[0].contains("1"))
				continue;
			
			
			if(temp[0].equals(userZip)) {
				if(temp.length>=11&&temp[2].equals("CommandEvent"))
					commandNum++;
				if(i==list.size()-1) {
					List<String> outList=new ArrayList<String>();
					outList.add(userZip+","+Integer.toString(commandNum)+","+Long.toString(FileSize.getFileSize(userZip)));
					CSVUtils.exportCsv(new File("CommandNumber.csv"), outList);
				}
			}
			else {
				
				if(!userZip.equals("")) {
					List<String> outList=new ArrayList<String>();
					outList.add(userZip+","+Integer.toString(commandNum)+","+Long.toString(FileSize.getFileSize(userZip)));
					CSVUtils.exportCsv(new File("CommandNumber.csv"), outList);
					
					if(temp.length>=11&&temp[2].equals("CommandEvent"))
						commandNum=1;
					else
						commandNum=0;
					
					userZip=new String(temp[0]);
				}
				else {
					if(temp.length>=11&&temp[2].equals("CommandEvent"))
						commandNum=1;
					else
						commandNum=0;
					userZip=new String(temp[0]);
				}
				
			}
		}
	}
	*/
	
	/*
	public void getUserPassRate() {
		
		List<String> list=CSVUtils.importCsv(new File("TestEventList.csv"));
		double successTimes=0;
		double runsTimes=0;
		
		String userZip=new String("");

		for(int i=1;i<list.size();i++) {

			String[] temp=list.get(i).split(",");
			
			if(temp[0].equals(userZip)) {
				successTimes+=Integer.parseInt(temp[6]);
				runsTimes+=Integer.parseInt(temp[5]);
				if(i==list.size()-1) {
					
					if(runsTimes==0) 
						runsTimes=1;//防止分母为0

					List<String> outList=new ArrayList<String>();
					outList.add(userZip+","+Double.toString(successTimes/runsTimes));
					CSVUtils.exportCsv(new File("UserTestPassRate.csv"), outList);
				}
			}
			else {
				
				if(!userZip.equals("")) {
					
					if(runsTimes==0) 
						runsTimes=1;
					
					List<String> outList=new ArrayList<String>();
					outList.add(userZip+","+Double.toString(successTimes/runsTimes));
					CSVUtils.exportCsv(new File("UserTestPassRate.csv"), outList);
					
					successTimes=Integer.parseInt(temp[6]);
					runsTimes=Integer.parseInt(temp[5]);
					userZip=new String(temp[0]);
				}
				else {
					successTimes=Integer.parseInt(temp[6]);
					runsTimes=Integer.parseInt(temp[5]);
					userZip=new String(temp[0]);
				}
				
			}
		}
	}
	*/
	
}
