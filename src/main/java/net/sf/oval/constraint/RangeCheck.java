/*********************************************************************
 * Copyright 2005-2018 by Sebastian Thomschke and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package net.sf.oval.constraint;

import static net.sf.oval.Validator.*;

import java.util.Map;

import net.sf.oval.ConstraintTarget;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

/**
 * @author Sebastian Thomschke
 */
public class RangeCheck extends AbstractAnnotationCheck<Range> {
   private static final long serialVersionUID = 1L;

   private double min = Double.MIN_VALUE;
   private double max = Double.MAX_VALUE;

   @Override
   public void configure(final Range constraintAnnotation) {
      super.configure(constraintAnnotation);
      setMax(constraintAnnotation.max());
      setMin(constraintAnnotation.min());
   }

   @Override
   protected Map<String, String> createMessageVariables() {
      final Map<String, String> messageVariables = getCollectionFactory().createMap(2);
      messageVariables.put("max", Double.toString(max));
      messageVariables.put("min", Double.toString(min));
      return messageVariables;
   }

   @Override
   protected ConstraintTarget[] getAppliesToDefault() {
      return new ConstraintTarget[] {ConstraintTarget.VALUES};
   }

   public double getMax() {
      return max;
   }

   public double getMin() {
      return min;
   }

   @Override
   public boolean isSatisfied(final Object validatedObject, final Object valueToValidate, final OValContext context, final Validator validator) {
      if (valueToValidate == null)
         return true;

      if (valueToValidate instanceof Number) {
         final double doubleValue = ((Number) valueToValidate).doubleValue();
         return doubleValue >= min && doubleValue <= max;
      }

      final String stringValue = valueToValidate.toString();
      try {
         final double doubleValue = Double.parseDouble(stringValue);
         return doubleValue >= min && doubleValue <= max;
      } catch (final NumberFormatException e) {
         return false;
      }
   }

   public void setMax(final double max) {
      this.max = max;
      requireMessageVariablesRecreation();
   }

   public void setMin(final double min) {
      this.min = min;
      requireMessageVariablesRecreation();
   }
}
