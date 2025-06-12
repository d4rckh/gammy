import PageTitle from "@/components/pageTitle";
import ParameterList from "@/components/ParameterList";
import CreateOrEditParameterDialog from "@/components/parameters/CreateOrEditParameterDialog";

export default function GameParameters() {
  return <>
    <PageTitle title="Game Parameters"  description="Game parameters are for tweaking various aspects of your game while also being able to create player overrides." />

    <CreateOrEditParameterDialog />

    <ParameterList />
  </>;
}