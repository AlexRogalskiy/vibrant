package org.vibrant.core.base.node

import kotlinx.coroutines.experimental.async
import org.vibrant.core.base.BaseJSONSerializer
import org.vibrant.core.base.jsonrpc.JSONRPCRequest
import org.vibrant.core.base.jsonrpc.JSONRPCResponse
import org.vibrant.core.base.models.BaseTransactionModel
import org.vibrant.core.node.RemoteNode

open class BaseJSONRPCProtocol(val node: BaseNode) {


    @JSONRPCMethod
    fun addTransaction(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*>{
        val transaction = BaseJSONSerializer().deserialize(request.params[0].toString()) as BaseTransactionModel
        if(node is BaseMiner){

        }
        return JSONRPCResponse(
                result = node is BaseMiner,
                error = null,
                id = request.id
        )
    }


    @JSONRPCMethod
    fun getLastBlock(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*>{
        return JSONRPCResponse(
                result = BaseJSONSerializer().serialize(node.chain.latestBlock()),
                error = null,
                id = request.id
        )
    }


    @JSONRPCMethod
    fun syncWithMe(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*>{
        node.possibleAheads.add(remoteNode)
        return JSONRPCResponse(
                result = true,
                error = null,
                id = request.id
        )
    }


    @JSONRPCMethod
    fun getFullChain(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*>{
        return JSONRPCResponse(
                result = BaseJSONSerializer().serialize(node.chain.produce(BaseJSONSerializer())),
                error = null,
                id = request.id
        )
    }


    @JSONRPCMethod
    fun echo(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*> {
        return JSONRPCResponse(
                result = request.params[0],
                error = null,
                id = request.id
        )
    }

    fun invoke(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*> {
        return this::class.java.getMethod(request.method, JSONRPCRequest::class.java, RemoteNode::class.java).invoke(this, request, remoteNode) as JSONRPCResponse<*>
    }
}