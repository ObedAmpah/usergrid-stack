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
package org.usergrid.persistence.ir.result;

import org.usergrid.persistence.EntityRef;
import org.usergrid.persistence.Results;
import org.usergrid.persistence.SimpleEntityRef;

import java.util.ArrayList;
import java.util.List;

public class EntityRefLoader implements ResultsLoader {

  private String type;
  
  public EntityRefLoader(String type) {
    this.type = type;
  }

  /* (non-Javadoc)
   * @see org.usergrid.persistence.ir.result.ResultsLoader#getResults(java.util.List)
   */
  @Override
  public Results getResults(List<ScanColumn> entityIds) throws Exception {
    Results r = new Results();
    List<EntityRef> refs = new ArrayList<EntityRef>(entityIds.size());
    for (ScanColumn id : entityIds) {
      refs.add(new SimpleEntityRef(type, id.getUUID()));
    }
    r.setRefs(refs);
    return r;
  }

}
