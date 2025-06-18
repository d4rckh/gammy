import {Button} from "@/components/ui/button";
import {useDeleteParameter} from "@/lib/api/mutations";
import type {components} from "@/lib/api/v1";

export default function DeleteParameterButton({parameter}: { parameter: components["schemas"]["ParameterEntity"] }) {
    const deleteOverride = useDeleteParameter();

    return <Button
        onClick={() => {
            deleteOverride.mutate({
                body: parameter
            })
        }}
        size={"sm"} variant={"destructive"}>
        Delete
    </Button>
}