/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.usergrid.batch;

import org.usergrid.batch.JobRuntime;

/**
 * Methods to allow job executions to interact with the distributed runtime
 * 
 * @author tnine
 *
 */
public interface JobRuntimeService {
  
  /**
   * Perform any heartbeat operations required.  Update jobExecution with the appropriate data
   * @param execution The job execution to update
   * @param delay The delay 
   * 
   * @throws JobExecutionException 
   */
  public void heartbeat(JobRuntime execution, long delay);
  
  /**
   * Heartbeat with the system defaults.  Update jobExecution with the appropriate data
   * @param execution The execution
   */
  public void heartbeat(JobRuntime execution);
  
  /**
   * Delay this exeuction
   * @param execution
   */
  public void delay(JobRuntime execution);

}
