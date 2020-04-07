package utcn.labs.sd.bankingservice.domain.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utcn.labs.sd.bankingservice.core.configuration.SwaggerTags;

import java.security.Principal;

@Api(tags = {SwaggerTags.BANKING_SERVICE_TAG})
@Controller
@CrossOrigin
public class SecurityController {

    @RequestMapping(value = "bank/login", method = RequestMethod.GET)
    @ApiOperation(value = "login", tags = SwaggerTags.LOGIN_TAG)
    @ResponseBody
    public Principal currentUserName(Principal principal) {
        return principal;
    }
}