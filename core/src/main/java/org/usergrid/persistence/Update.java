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
package org.usergrid.persistence;

import java.util.ArrayList;
import java.util.List;

public class Update {

	private List<UpdateOperation> operationList = new ArrayList<UpdateOperation>();

	public class UpdateOperation {
		String propertyName;
		UpdateOperator operator;
		Object value;

		UpdateOperation(String propertyName, UpdateOperator operator,
				Object value) {
			this.propertyName = propertyName;
			this.operator = operator;
			this.value = value;
		}

		public String getPropertyName() {
			return propertyName;
		}

		public UpdateOperator getOperator() {
			return operator;
		}

		public Object getValue() {
			return value;
		}
	}

	public static enum UpdateOperator {
		UPDATE, DELETE, ADD_TO_LIST, REMOVE_FROM_LIST;
	}

	public Update() {
	}

	public void add(String propertyName, UpdateOperator operator, Object value) {
		UpdateOperation operation = new UpdateOperation(propertyName, operator,
				value);
		operationList.add(operation);
	}

	public void clear() {
		operationList = new ArrayList<UpdateOperation>();
	}

}
