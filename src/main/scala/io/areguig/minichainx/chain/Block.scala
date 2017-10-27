package io.areguig.minichainx.chain

import java.security.MessageDigest

case class Block( index: Int,
             prevHash: String,
             timestamp: Long,
             data: String,
             hash: String)

object Block {

  def genesisBlock(): Block = {
    val curr = System.currentTimeMillis / 1000
    val genesisData = "Genesis"
     Block(0, "0", curr, genesisData, calculateHash(0, "0", curr, genesisData))
  }

  val calculateHash = (index: Int, prevHash: String, timestamp: Long, data: String) => {
    MessageDigest.getInstance("SHA-256").digest((index + prevHash + timestamp + data).getBytes).toString
  }

}