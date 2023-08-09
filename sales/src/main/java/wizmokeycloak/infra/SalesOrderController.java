package wizmokeycloak.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wizmokeycloak.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/salesOrders")
@Transactional
public class SalesOrderController {

    @Autowired
    SalesOrderRepository salesOrderRepository;

    @RequestMapping(
        value = "salesOrders/",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public SalesOrder createSalesOrder(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody SalesOrder salesOrder
    ) throws Exception {
        System.out.println("##### /salesOrder/createSalesOrder  called #####");
        salesOrder.createSalesOrder(createSalesOrdercommand);
        salesOrderRepository.save(salesOrder);
        return salesOrder;
    }

    @RequestMapping(
        value = "salesOrders/{id}/updatesalesorder",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public SalesOrder updateSalesOrder(
        @PathVariable(value = "id") String id,
        @RequestBody UpdateSalesOrderCommand updateSalesOrderCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /salesOrder/updateSalesOrder  called #####");
        Optional<SalesOrder> optionalSalesOrder = salesOrderRepository.findById(
            id
        );

        optionalSalesOrder.orElseThrow(() -> new Exception("No Entity Found"));
        SalesOrder salesOrder = optionalSalesOrder.get();
        salesOrder.updateSalesOrder(updateSalesOrderCommand);

        salesOrderRepository.save(salesOrder);
        return salesOrder;
    }
}
//>>> Clean Arch / Inbound Adaptor
