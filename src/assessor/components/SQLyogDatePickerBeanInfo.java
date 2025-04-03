/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assessor.components;

import java.beans.*;

public class SQLyogDatePickerBeanInfo extends SimpleBeanInfo {
    public BeanDescriptor getBeanDescriptor() {
        return new BeanDescriptor(SQLyogDatePicker.class, null);
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            return new PropertyDescriptor[] {
                new PropertyDescriptor("selectedDate", SQLyogDatePicker.class)
            };
        } catch (IntrospectionException e) {
            return super.getPropertyDescriptors();
        }
    }
}