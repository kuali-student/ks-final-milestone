package org.kuali.student.deploy.spring;

import org.kuali.common.impex.spring.MpxSupplierConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ MpxSupplierConfig.class })
public class ImportConfig {
}