/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.google.contact.transformer;

import java.util.List;

import org.mule.api.transformer.TransformerException;
import org.mule.common.bulk.BulkItem;
import org.mule.common.bulk.BulkOperationResult;
import org.mule.common.bulk.BulkOperationResult.BulkOperationResultBuilder;
import org.mule.modules.google.api.domain.BatchResult;
import org.mule.transformer.AbstractDiscoverableTransformer;
import org.mule.transformer.types.DataTypeFactory;

import com.google.gdata.data.BaseEntry;
import com.google.gdata.data.batch.BatchStatus;

public class BatchResultToBulkOperationTransformer extends AbstractDiscoverableTransformer {

	public BatchResultToBulkOperationTransformer() {
		this.registerSourceType(DataTypeFactory.create(List.class, BatchResult.class, null));
		this.setReturnDataType(DataTypeFactory.create(BulkOperationResult.class));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected Object doTransform(Object src, String enc) throws TransformerException {
		List<BatchResult> results = (List<BatchResult>) src;
		
		BulkOperationResultBuilder<BaseEntry<?>> builder = BulkOperationResult.<BaseEntry<?>>builder();
		
		if (results != null) {
			for (BatchResult result : results) {
				BatchStatus status = result.getStatus();
				int code = status.getCode();
				
				builder.addItem(BulkItem.<BaseEntry<?>>builder()
						.setRecordId(result.getId())
						.setPayload(result.getEntry())
						.setMessage(status.getContent())
						.setStatusCode(String.format("%d - %s", code, status.getReason()))
						.setSuccessful(code == 200 || code == 201 || code == 204)
					);
			}
		}
		
		return builder.build();
	}

}
