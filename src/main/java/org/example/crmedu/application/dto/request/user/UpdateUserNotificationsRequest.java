package org.example.crmedu.application.dto.request.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateUserNotificationsRequest {

  private Boolean notifyMode;
}
