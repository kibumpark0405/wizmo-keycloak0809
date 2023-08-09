package wizmokeycloak.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import wizmokeycloak.SalesApplication;
import wizmokeycloak.domain.SalesOrderDeleted;

@Entity
@Table(name = "SalesOrder_table")
@Data
//<<< DDD / Aggregate Root
public class SalesOrder {

    @Id
    private String salesOrderNumber;

    private String salesPerson;

    private SalesType salesType;

    @Embedded
    private CompanyId companyId;

    @ElementCollection
    private List<SalesItem> salesItems;

    @PostPersist
    public void onPostPersist() {
        SalesOrderDeleted salesOrderDeleted = new SalesOrderDeleted(this);
        salesOrderDeleted.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {}

    public static SalesOrderRepository repository() {
        SalesOrderRepository salesOrderRepository = SalesApplication.applicationContext.getBean(
            SalesOrderRepository.class
        );
        return salesOrderRepository;
    }

    //<<< Clean Arch / Port Method
    public void createSalesOrder(
        CreateSalesOrderCommand createSalesOrderCommand
    ) {
        //implement business logic here:

        SalesOrderCreated salesOrderCreated = new SalesOrderCreated(this);
        salesOrderCreated.set(createSalesOrderCommand.get());
        salesOrderCreated.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void updateSalesOrder(
        UpdateSalesOrderCommand updateSalesOrderCommand
    ) {
        //implement business logic here:

        SalesOrderUpdated salesOrderUpdated = new SalesOrderUpdated(this);
        salesOrderUpdated.set(updateSalesOrderCommand.get());
        salesOrderUpdated.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
