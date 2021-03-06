/*********************************************************************
 * Copyright 2005-2018 by Sebastian Thomschke and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package net.sf.oval.integration.spring;

import net.sf.oval.Check;
import net.sf.oval.configuration.CheckInitializationListener;
import net.sf.oval.constraint.CheckWithCheck;

/**
 * Injects Spring bean dependencies into {@link Check} instances.
 *
 * Required dependencies must be annotated with <code>@Autowired</code> within {@link Check} the class.
 *
 * Requires the {@link SpringInjector} be setup correctly.
 *
 * @author Sebastian Thomschke
 */
public class SpringCheckInitializationListener implements CheckInitializationListener {
   public static final SpringCheckInitializationListener INSTANCE = new SpringCheckInitializationListener();

   @Override
   public void onCheckInitialized(final Check check) {
      SpringInjector.get().inject(check);

      if (check instanceof CheckWithCheck) {
         final CheckWithCheck checkWith = (CheckWithCheck) check;
         SpringInjector.get().inject(checkWith.getSimpleCheck());
      }
   }
}
