package com.rxnctrllabs.trinamic.command;

import com.rxnctrllabs.trinamic.command.calculation.CalculationCommand;
import com.rxnctrllabs.trinamic.command.control.CompareCommand;
import com.rxnctrllabs.trinamic.command.control.JumpAlwaysCommand;
import com.rxnctrllabs.trinamic.command.control.JumpConditionalCommand;
import com.rxnctrllabs.trinamic.command.control.StopProgramCommand;
import com.rxnctrllabs.trinamic.command.control.WaitCommand;
import com.rxnctrllabs.trinamic.command.io.GetInputCommand;
import com.rxnctrllabs.trinamic.command.io.SetOutputCommand;
import com.rxnctrllabs.trinamic.command.motion.MoveToPositionCommand;
import com.rxnctrllabs.trinamic.command.motion.RotateLeftCommand;
import com.rxnctrllabs.trinamic.command.motion.RotateRightCommand;
import com.rxnctrllabs.trinamic.command.motion.StopRotationCommand;
import com.rxnctrllabs.trinamic.command.parameter.axis.GetAxisParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.axis.RestoreAxisParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.axis.SetAxisParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.axis.StoreAxisParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.global.GetGlobalParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.global.RestoreGlobalParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.global.SetGlobalParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.global.StoreGlobalParameterCommand;
import com.rxnctrllabs.trinamic.command.script.ApplicationStatusCommand;
import com.rxnctrllabs.trinamic.command.script.ExitDownloadModeCommand;
import com.rxnctrllabs.trinamic.command.script.ResetApplicationCommand;
import com.rxnctrllabs.trinamic.command.script.RunApplicationCommand;
import com.rxnctrllabs.trinamic.command.script.StartDownloadModeCommand;
import com.rxnctrllabs.trinamic.command.script.StopApplicationCommand;

public interface ICommandVisitor {

    void visit(CalculationCommand command);

    void visit(CompareCommand command);

    void visit(JumpAlwaysCommand command);

    void visit(JumpConditionalCommand command);

    void visit(StopProgramCommand command);

    void visit(WaitCommand command);

    void visit(GetInputCommand command);

    void visit(SetOutputCommand command);

    void visit(RotateRightCommand command);

    void visit(RotateLeftCommand command);

    void visit(StopRotationCommand command);

    void visit(MoveToPositionCommand command);

    void visit(GetAxisParameterCommand command);

    void visit(RestoreAxisParameterCommand command);

    void visit(StoreAxisParameterCommand command);

    void visit(SetAxisParameterCommand command);

    void visit(GetGlobalParameterCommand command);

    void visit(RestoreGlobalParameterCommand command);

    void visit(StoreGlobalParameterCommand command);

    void visit(SetGlobalParameterCommand command);

    void visit(StartDownloadModeCommand command);

    void visit(ExitDownloadModeCommand command);

    void visit(RunApplicationCommand command);

    void visit(StopApplicationCommand command);

    void visit(ResetApplicationCommand command);

    void visit(ApplicationStatusCommand command);
}
