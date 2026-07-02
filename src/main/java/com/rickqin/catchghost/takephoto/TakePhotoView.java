package com.rickqin.catchghost.takephoto;

import com.rickqin.catchghost.base.ui.NotiBox;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.InMemoryUploadHandler;
import com.vaadin.flow.server.streams.UploadHandler;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

@Route("")
@PageTitle("Take Photo")
class TakePhotoView extends VerticalLayout implements BeforeEnterObserver {

    // A 6-digit number.
    private final String workorderRegex;

    final TextField txtWorkOrder;
    final Upload upload;
    final NotiBox notibox = new NotiBox();

    final PhotoService photoService;

    public TakePhotoView(@Value("${app.workorder.validation}") String workorderRegex, PhotoService photoService) {
        this.workorderRegex = workorderRegex;
        this.photoService = photoService;

        Button btnTakePhoto = new Button("Take Photo");
        btnTakePhoto.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        InMemoryUploadHandler inMemoryHandler = UploadHandler.inMemory((metadata, data) -> {
            // Get other information about the file.
            String fileName = metadata.fileName();
            String mimeType = metadata.contentType();
            long contentLength = metadata.contentLength();
            
            // Do something with the file data...
            // processFile(data, fileName);
        });
        
        upload = new Upload(inMemoryHandler);
        upload.setUploadButton(btnTakePhoto);
        upload.setAutoUpload(true);
        upload.setMaxFiles(20);
        upload.setDropAllowed(false);
        
        txtWorkOrder = new TextField("Work Order");
        txtWorkOrder.setClearButtonVisible(true);
//        txtWorkOrder.addInputListener(evt -> {
//            upload.setEnabled(validateWorkorder(txtWorkOrder.getValue()));
//        });
        txtWorkOrder.addValueChangeListener(evt -> {
            upload.setEnabled(validateWorkorder(txtWorkOrder.getValue()));
        });
        txtWorkOrder.addBlurListener(evt -> {
            upload.setEnabled(validateWorkorder(txtWorkOrder.getValue()));
        });

        setAlignItems(Alignment.CENTER);
        add(new H2("Work Photo"), txtWorkOrder, upload);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Map<String, List<String>> params = event.getLocation()
                .getQueryParameters()
                .getParameters();
        // URL参数合法性验证
        if (params.containsKey("workorder")) {
            String workorder = params.get("workorder").get(0);
            if (workorder != null && workorder.matches(workorderRegex)) {
                txtWorkOrder.setValue(workorder);
                txtWorkOrder.setReadOnly(true);
                upload.setEnabled(true);
            } else {
                prepareForManualEnterWorkOrder();
                notibox.open("Workorder number is invalid. Please enter it manually.", NotiBox.NotiType.ERROR);
            }
        } else {
            prepareForManualEnterWorkOrder();
        }
    }
    
    private boolean validateWorkorder(String workorder) {
        return StringUtils.isNotBlank(workorder) && workorder.matches(workorderRegex);
    }
    
    private void prepareForManualEnterWorkOrder() {
        txtWorkOrder.clear();
        txtWorkOrder.setReadOnly(false);
        upload.setEnabled(false);
    }

//    private void createTask() {
//        taskService.createTask(description.getValue(), dueDate.getValue());
//        taskGrid.getDataProvider().refreshAll();
//        description.clear();
//        dueDate.clear();
//        Notification.show("Task added", 3000, Notification.Position.BOTTOM_END)
//                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
//    }
}
