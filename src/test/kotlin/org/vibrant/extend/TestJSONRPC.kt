package org.vibrant.extend

import org.junit.Assert.assertEquals
import org.junit.Test
import org.vibrant.core.base.BaseJSONSerializer
import org.vibrant.core.base.jsonrpc.JSONRPCRequest
import org.vibrant.core.base.jsonrpc.JSONRPCResponse
import org.vibrant.core.base.node.BaseJSONRPCProtocol
import org.vibrant.core.base.node.BaseNode

class TestJSONRPC {


    @Test
    fun `JSON RPC response serialization`(){
        val request = JSONRPCResponse(
                result = "Hood",
                error = null,
                id = "1"
        )

        val serialized = BaseJSONSerializer().serialize(request)
        assertEquals(
                "{\"result\":\"Hood\",\"error\":null,\"id\":\"1\",\"version\":\"2.0\"}",
                serialized
        )
    }

    @Test
    fun `JSON RPC response deserialization`(){
        val request = JSONRPCResponse(
                result = "Hood",
                error = null,
                id = "1"
        )

        val serialized = BaseJSONSerializer().serialize(request)
        val deserialized = BaseJSONSerializer().deserializeJSONRPC(serialized)
        assertEquals(
                request,
                deserialized
        )
    }

    @Test
    fun `JSON RPC request serialization`(){
        val request = JSONRPCRequest(
                "callWithNoParams",
                arrayOf(),
                "1"
        )

        val serialized = BaseJSONSerializer().serialize(request)
        assertEquals(
                "{\"method\":\"callWithNoParams\",\"params\":[],\"id\":\"1\",\"version\":\"2.0\"}",
                serialized
        )
    }

    @Test
    fun `JSON RPC request deserialization`(){
        val request = JSONRPCRequest(
                "callWithNoParams",
                arrayOf(),
                "1"
        )

        val serialized = BaseJSONSerializer().serialize(request)
        val deserialized = BaseJSONSerializer().deserializeJSONRPC(serialized)

        assertEquals(
                request,
                deserialized
        )
    }


    @Test
    fun `JSON RPC request invocation`(){
        val request = JSONRPCRequest(
                "echo",
                arrayOf("Hello"),
                "1"
        )

        val fakeProtocol = BaseJSONRPCProtocol(BaseNode(1324))




        val some = fakeProtocol.invoke(request)
        assertEquals(
                JSONRPCResponse(result="Hello", error=null, id="1", version="2.0"),
                some
        )


    }



}