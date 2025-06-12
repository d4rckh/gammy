import PageTitle from "@/components/pageTitle";
import ParameterList from "@/components/ParameterList";
import CreateOrEditParameterDialog from "@/components/parameters/CreateOrEditParameterDialog";

export default function GameParameters() {
  return <>
    <PageTitle title="Game Parameters" />

    <CreateOrEditParameterDialog />

    <ParameterList />
  </>;
}