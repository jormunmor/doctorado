/*
 * Copyright 2014 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.optaplanner.examples.vehiclerouting.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.optaplanner.examples.common.domain.AbstractPersistable;

/**
 *
 * @author jorge
 */
@XStreamAlias("TaskDependency")
public class TaskDependency extends AbstractPersistable {
  
    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof TaskDependency)) {
            return false;
       
       } else if (obj == this) {
            return true;
       
       }
       
       boolean isEqual = false;
       
       TaskDependency task = (TaskDependency) obj;
       if(this.getId() == task.getId()){
           isEqual = true;
           
       }
       
       return isEqual;
       
    }
    
    public int compareTo(TaskDependency other) {
        int comp = -1;
        if(this.equals(other)){
            comp = 0;
            
        } else if(this.getId() > other.getId()){
            comp = 1;
            
        } else{
            comp = -1;
            
        }
        
        return comp;
        
    }
   
}
