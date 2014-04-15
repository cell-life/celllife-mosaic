package org.celllife.mosaic.interfaces.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import org.celllife.mosaic.framework.restclient.RESTClient

@Controller
class ReportController {

	@Autowired
	RESTClient client;

    @Value('${external.base.url}')
    def String externalBaseUrl;

    @RequestMapping("/")
    def index(Model model) {
        getReports(model)
    }

    @RequestMapping(value="index", method = RequestMethod.GET)
    def getReports(Model model) {

        def reports = client.get("${externalBaseUrl}/service/reports")
        model.put("reports", reports)
        return "index";

    }

}
