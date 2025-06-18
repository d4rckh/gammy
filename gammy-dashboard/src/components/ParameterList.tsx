import {useParameters} from "@/lib/api/queries"
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "./ui/table";
import CreateOrEditParameterDialog from "./parameters/CreateOrEditParameterDialog";
import DeleteParameterButton from "./parameters/DeleteParameterButton";

export default function ParameterList() {
    const parameters = useParameters();

    return parameters.data && <>
        <Table className="mt-2">
            <TableHeader>
                <TableRow>
                    <TableHead>ID</TableHead>
                    <TableHead>Path</TableHead>
                    <TableHead>Value</TableHead>
                    <TableHead>Actions</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    parameters.data.map(parameter =>
                        <TableRow>
                            <TableCell>{parameter.id}</TableCell>
                            <TableCell>{parameter.path}</TableCell>
                            <TableCell>{parameter.value}</TableCell>
                            <TableCell className="flex flex-wrap gap-2">
                                <CreateOrEditParameterDialog parameter={parameter}/>
                                <DeleteParameterButton parameter={parameter}/>
                            </TableCell>
                        </TableRow>
                    )
                }
            </TableBody>
        </Table>
    </>
}