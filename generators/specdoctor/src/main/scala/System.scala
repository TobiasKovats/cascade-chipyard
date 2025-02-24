/* SpecDoctor IO Binder */
package specdoctor

import chisel3._
import freechips.rocketchip.config.{Field, Config}
import freechips.rocketchip.diplomacy.LazyModuleImp
import freechips.rocketchip.subsystem.BaseSubsystem

case object SpecDoctorKey extends Field[Boolean](false)

class SpecDoctorIO extends Bundle {
  val check = Input(Bool())
  val done = Output(Bool())
}

trait CanHaveSpecDoctor { this: BaseSubsystem =>
  None
}

trait CanHaveSpecDoctorModuleImp extends LazyModuleImp {
  val outer: CanHaveSpecDoctor
  val clock: Clock
  val reset: Reset

  val spdoc = if (p(SpecDoctorKey)) {
    val spdoc_io = IO(new SpecDoctorIO)
    chisel3.dontTouch(spdoc_io)

    Some(spdoc_io)
  } else {
    None
  }
}

class WithSpecDoctor extends Config((site, here, up) => {
  case SpecDoctorKey => true
})
