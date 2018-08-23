# PAThWay: Performance Analysis and Tuning Workflows

A detailed user guide is available [here](How_to_use_Pathway.pdf).

## Introduction

Pathway is a tool for designing and executing performance engineering workflows for HPC applications. Having a formal model of the workflow helps to organize and guide the whole optimization process.
Installation

## On a Debian-based Linux system, use the following instructions to install Pathway:

    Install libwebkitgtk: sudo apt-get install libwebkitgtk-1.0-0
    Install git: sudo apt-get install git
    Download the Luna release of Eclipse
    In Eclipse, add the following update site: http://periscope.in.tum.de/pathway/eclipse/
    Install Pathway from the update site

## Instructions for Microsoft Windows:

    Install a port of git, like msysgit. The command git has to be in your PATH.
    Download the Luna release of Eclipse
    In Eclipse, add the following update site: http://periscope.in.tum.de/pathway/eclipse/
    Install Pathway from the update site

## Quick start

### Creating a new project and workflow

Start the Eclipse installation in which you have installed Pathway and open the Pathway perspective with the “+” button in the upper right corner. Remember that you can always reset the perspective’s layout to its default by right-clicking on the corresponding button and selecting “reset”.

![alt text](http://periscope.in.tum.de/wp-content/uploads/2015/03/perspective.png)

Before you can start working on any workflow, you need to create a Pathway project from within the Navigator view.

![alt text](http://periscope.in.tum.de/wp-content/uploads/2015/03/new_project.png)

Within this project, you can then create a new workflow. The wizard also offers some pre-defined workflows to start from.

### Editing the workflow

Designing one’s own workflow requires knowledge about the jBPM workflow editor and Pathway workflows in general. We strongly recommend to start with an existing workflow from the “New workflow” wizard.

In the “Pathway” perspective, you have the workflow editor in the center. Within the editor, you can graphically arrange and connect the individual activities of the workflow. New activities can be added from the toolbox. Most Pathway workflows will at least have an activity for creating the batch system manager and for creating and running experiments. Experiments are jobs to be scheduled on the HPC system. When running experiments, Pathway automatically creates a job script according to the configured type of batch scheduler.

When adding new activities, pay attention to the properties view (shown below). Some activities require mapping parameters and results to be set in order to function properly. If you look at the properties of the workflow itself, you will see that there are workflow variables defined, which can be mapped as a parameter or result for the individual activities.

![alt text](http://periscope.in.tum.de/wp-content/uploads/2015/03/properties.png)

### Configure your application

Before you can run a workflow with your application as the test subject, you need to let Pathway know how to use your application. To open the application configuration dialog, select “Pathway” from the menu and go to “Edit applications”. Fill in the following fields:

    Executable name: The name of the executable file to run
    Arguments: Arguments to pass to the executable
    Make directory: If Pathway is supposed to call make, this indicates the location of the makefile
    Execution directory: Where the executable can be found (and is executed)
    Wall-time limit: The wall-time limit for the job, in minutes

Note that you can use placeholders in many of those fields. Using a placeholder, you can insert the value of a workflow variable during the execution of the workflow. The general syntax is: {#VARIABLE:varName#}. So if you want to run executables from test1 to test4 for example, you can specify “test{#VARIABLE:i#}” as the executable name, where i is a workflow variable you increment within the logic of your workflow.

### Executing the workflow

Before you can execute the workflow, you need an application on a HPC system that you want to investigate. Therefore, you first need to configure your HPC system and your application in the Pathway menu.

![alt text](http://periscope.in.tum.de/wp-content/uploads/2015/03/execution_parameters.png)

When this is done, you can select your system and application in the execution parameters view shown to the right. You may want to specify some of the other parameters also. Not all parameters need to be specified, but Pathway may ask you then during workflow execution for the required information. In general, it is more comfortable to set the values up-front.

There are several possible formats in which you can specify the number of MPI processes and OpenMP threads to use. Since Pathway is all about automation, it is possible to specify a range of numbers and the experiment will then be performed for each configuration. Below is a list of possible formats:

    Single number, e.g. “5”: performs the experiment one time
    List of numbers, e.g. “5, 12, 20”: performs the experiment three times
    Range of numbers, e.g. “4:8”: performs the experiment five times (for 4 … 8 threads)
    Range with step size, e.g. “2:8:2”: performs the experiment four times (for 2, 4, 6 and 8 threads)

Finally, click on the “Start workflow” button to start the workflow execution.

![alt text](http://periscope.in.tum.de/wp-content/uploads/2015/03/start_workflow.png)

Pathway will change to the execution perspective, in which the progress of the workflow execution is shown. In the editor-like view, the workflow is shown, with the current activity highlighted. Another important view is the log window, where you can find specific information about what Pathway is currently doing.

### Browsing results

In order to see the results of the workflow execution, switch to the “READEX (Browse)” perspective. There, you will find a list of recently performed experiments. Selecting one experiment allows you to see all its details below. If an experiment does not show up in the list, try refreshing the view (from the Pathway menu).

### Support

For problem reports and feedback please write at https://www.readex.eu/index.php/contact/.
