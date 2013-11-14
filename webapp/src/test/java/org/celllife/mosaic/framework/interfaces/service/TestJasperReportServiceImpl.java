package org.celllife.mosaic.framework.interfaces.service;

import org.celllife.pconfig.model.Pconfig;
import org.junit.Before;
import org.celllife.reporting.service.ReportService;
import org.celllife.mosaic.test.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Ignore
@ContextConfiguration(classes = TestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestJasperReportServiceImpl {

    @Autowired
    ReportService jasperReportService;

    @Test
    public void testGetReports() {

        List<Pconfig> pconfigList = jasperReportService.getReports();

    }

}