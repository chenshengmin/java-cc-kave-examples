/**
 * Copyright 2016 University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package examples;

import java.io.File;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cc.kave.commons.model.events.CommandEvent;
import cc.kave.commons.model.events.IDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.testrunevents.TestCaseResult;
import cc.kave.commons.model.events.testrunevents.TestResult;
import cc.kave.commons.model.events.testrunevents.TestRunEvent;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.idecomponents.IDocumentName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;

/**
 * Simple example that shows how the interaction dataset can be opened, all
 * users identified, and all contained events deserialized.
 */

public class GettingStarted {

	private String eventsDir;

	public GettingStarted(String eventsDir) {
		this.eventsDir = eventsDir;
	}

	public void run() {

		//System.out.printf("looking (recursively) for events in folder %s\n", new File(eventsDir).getAbsolutePath());
		
		List<String> dataList=new ArrayList<String>();
        /*
		dataList.add("Developer,File,EventType,TriggeredTime,Duration");
        boolean isSuccess=CSVUtils.exportCsv(new File("EventList.csv"), dataList);
        */
        
		/*
        dataList=new ArrayList<String>();
        dataList.add("Developer,File,EventType,CommandID,TriggeredTime,Duration");
        CSVUtils.exportCsv(new File("RefactorCommand.csv"), dataList);
        */
        
		
		
		dataList=new ArrayList<String>();
        dataList.add("Developer,File,EventType,CommandID,TriggeredTime,Duration,Runs,Success,Failed,Error,Ignored");
        CSVUtils.exportCsv(new File("RefactorsAndQuickfixesBetweenTests.csv"), dataList);
        
		
        /*
		dataList=new ArrayList<String>();
		dataList.add("Developer,File,EventType,CommandID,TriggeredTime,Duration");
		CSVUtils.exportCsv(new File("CommandEventList.csv"), dataList);
		*/
		
        Set<String> userZips = IoHelper.findAllZips(eventsDir);

		int userNums=0;
		for (String userZip : userZips) {
			System.out.printf("\n#### processing user zip: %s #####\n", userZip);
			
			if(!userZip.equals("2016-09-26/100.zip")) {
				processUserZip(userZip);
			}
			
		}
	}
	
	private void processUserZip(String userZip) {
		/*
		List<String> dataList=new ArrayList<String>();
		dataList.add(userZip);
        CSVUtils.exportCsv(new File("CommandEventList.csv"), dataList);
        */
		
		int numProcessedEvents = 0;
		
		/*
		File f=new File(eventsDir, userZip);
		List<String> dataList=new ArrayList<String>();
		dataList.add(userZip+","+Long.toString(f.length()));
        CSVUtils.exportCsv(new File("FileSize.csv"), dataList);
        */
		
		
		try (IReadingArchive ra = new ReadingArchive(new File(eventsDir, userZip))) {
<<<<<<< HEAD
			while (ra.hasNext() /*&& (numProcessedEvents++ < 500)*/) {
				IDEEvent e = ra.getNext(IDEEvent.class);

				processEvent(e,userZip);
=======
			// ... and iterate over content.
			// the iteration will stop after 200 events to speed things up, remove this
			// guard to process all events.
			while (ra.hasNext() && (numProcessedEvents++ < 200)) {
				/*
				 * within the userZip, each stored event is contained as a single file that
				 * contains the Json representation of a subclass of IDEEvent.
				 */
				IDEEvent e = ra.getNext(IDEEvent.class);

				processEvent(e);
>>>>>>> 7bbafadbddb2b221eeafce3af75a4e9cf40c3e5e
			}
		}
		
	}

	private void processEvent(IDEEvent e,String userZip) {
		
		if(e instanceof TestRunEvent) {
			process((TestRunEvent) e,userZip);
		} else if(e instanceof CommandEvent) {
			process((CommandEvent) e,userZip);
		} else {
			//processBasic(e,userZip);
		}
	}

	private void process(CommandEvent e,String userZip) {
		
		String eventType = e.getClass().getSimpleName();
		ZonedDateTime triggerTime = e.getTriggeredAt();
		IDocumentName processFile=e.ActiveDocument;
		Duration duration=e.Duration;
		String commandId=e.getCommandId();
		
		if(!(commandId.contains("QuickFixes")||commandId.contains("refactor")||commandId.contains("Refactor")||commandId.contains("reFactor")||commandId.contains("ReFactor")))
			return;
		
		List<String> dataList=new ArrayList<String>();
		dataList.add(userZip+","+processFile+","+eventType+","+commandId+","+triggerTime+","+duration+","+"null"+","+"null"+","+"null"+","+"null"+","+"null");
		CSVUtils.exportCsv(new File("RefactorsAndQuickfixesBetweenTests.csv"), dataList);
	}

	private void process(CompletionEvent e) {

		ISST snapshotOfEnclosingType = e.context.getSST();
		String enclosingTypeName = snapshotOfEnclosingType.getEnclosingType().getFullName();

		System.out.printf("found a CompletionEvent (was triggered in: %s)\n", enclosingTypeName);
	}
	
	private void process(TestRunEvent e,String userZip) {
		
		String eventType = e.getClass().getSimpleName();
		ZonedDateTime triggerTime = e.getTriggeredAt();
		IDocumentName processFile=e.ActiveDocument;
		Duration duration=e.Duration;
		Integer runs=e.Tests.size();
		
		Integer success=0;
		Integer failed=0;
		Integer error=0;
		Integer ignored=0;
		
		for(TestCaseResult t:e.Tests) {
			if(t.Result==TestResult.Success) {
				success++;
			}else if(t.Result==TestResult.Failed) {
				failed++;
			}else if(t.Result==TestResult.Error) {
				error++;
			}else if(t.Result==TestResult.Ignored) {
				ignored++;
			}
		}
		
		List<String> dataList=new ArrayList<String>();
		dataList.add(userZip+","+processFile+","+eventType+","+"null"+","+triggerTime+","+duration+","+runs.toString()+","+success.toString()+","+failed.toString()+","+error.toString()+","+ignored.toString());
        CSVUtils.exportCsv(new File("RefactorsAndQuickfixesBetweenTests.csv"), dataList);
        
	}

	private void processBasic(IDEEvent e,String userZip) {
		
		String eventType = e.getClass().getSimpleName();
		ZonedDateTime triggerTime = e.getTriggeredAt();
		IDocumentName processFile=e.ActiveDocument;
		Duration duration=e.Duration;
		
		List<String> dataList=new ArrayList<String>();
		dataList.add(userZip+","+processFile+","+eventType+","+triggerTime+","+duration);
        boolean isSuccess=CSVUtils.exportCsv(new File("EventList.csv"), dataList);

	}
}