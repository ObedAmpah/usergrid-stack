package org.usergrid.persistence.ir.result;

import org.usergrid.persistence.Results;

import java.util.List;

public class IDLoader implements ResultsLoader {

  public IDLoader() {
  }

  /* (non-Javadoc)
   * @see org.usergrid.persistence.ir.result.ResultsLoader#getResults(java.util.List)
   */
  @Override
  public Results getResults(List<ScanColumn> entityIds) throws Exception {
    Results r = new Results();
    r.setIds(ScanColumnTransformer.getIds(entityIds));
    return r;
  }
}
