package com.tinqin.zoostore.api.operations.base;

public interface OperationProcessor<Response extends OperationResponse, Request extends OperationRequest> {

    Response process(Request input);
}
