
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

<<<<<<< HEAD
import examples.CompareFileDirectory;
import examples.GettingStarted;
import examples.GettingStartedContexts;
import examples.HandleFile;
import examples.Interval;
=======
import examples.CountEventTypeExample;
import examples.rsse.calls.BMNMining;
>>>>>>> 7bbafadbddb2b221eeafce3af75a4e9cf40c3e5e

public class RunMe {

	/*
	 * download the interaction data and unzip it into the root of this project (at
	 * the level of the pom.xml). Unpack it, you should now have a folder that
	 * includes a bunch of folders that have dates as names and that contain .zip
	 * files.
	 */
	public static String eventsDir = "/Volumes/Data/Events-170301-2";

	/*
	 * download the context data and follow the same instructions as before.
	 */
	public static String contextsDir = "Contexts-170503";

	public static void main(String[] args) {

<<<<<<< HEAD
		//new GettingStarted(eventsDir).run();
		//new GettingStartedContexts(contextsDir).run();
		new HandleFile().getPassRateWithQuickFixes();
=======
		// BASIC DATA READING
		
		// new GettingStarted(eventsDir).run();
		new CountEventTypeExample(eventsDir).run();
		// new GettingStartedContexts(contextsDir).run();
		
		// RSSE RELATED EXAMPLES
		new BMNMining().run();
>>>>>>> 7bbafadbddb2b221eeafce3af75a4e9cf40c3e5e
	}
};