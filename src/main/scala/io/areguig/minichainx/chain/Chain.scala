package io.areguig.minichainx.chain

import java.util

import io.areguig.minichainx.chain.Block.{calculateHash, genesisBlock}


object Chain {
  private val c = new util.LinkedHashMap[Int, Block];

  var get: () => util.LinkedHashMap[Int, Block] = () => {
    if (c.isEmpty) {
      val genesis = genesisBlock()
      c.put(genesis.index, genesis)
    }
    c
  }
  var generateNextBlock: String => Block = (blockData: String) => {
    val previousBlock = c.getOrDefault(c.size() - 1, genesisBlock())
    val nextIndex = previousBlock.index + 1
    val nextTimestamp = System.currentTimeMillis / 1000
    val nextHash = calculateHash(nextIndex, previousBlock.hash, nextTimestamp, blockData);
    Block(nextIndex, previousBlock.hash, nextTimestamp, blockData, nextHash);
  }

  def addBlock (block: Block) {
    c.put(block.index, block)
  }

}
