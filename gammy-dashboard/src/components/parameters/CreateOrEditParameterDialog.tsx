import {useCreateParameter, useUpdateParameter,} from "@/lib/api/mutations";
import type {components} from "@/lib/api/v1";
import {Dialog, DialogContent, DialogTitle, DialogTrigger,} from "../ui/dialog";
import {Button} from "../ui/button";
import {useEffect, useState} from "react";
import {Input} from "../ui/input";
import {Label} from "../ui/label";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue,} from "../ui/select";

type ParameterEntity = components["schemas"]["ParameterEntity"];
type ParameterType = components["schemas"]["ParameterType"];

export default function CreateOrEditParameterDialog({
                                                        parameter,
                                                    }: {
    parameter?: ParameterEntity;
}) {
    const create = useCreateParameter();
    const update = useUpdateParameter();
    const [open, setOpen] = useState(false);

    const [body, setBody] = useState<ParameterEntity>({
        path: "",
        type: "STRING",
        value: "",
    });

    const [errors, setErrors] = useState<{ [key: string]: boolean }>({});

    useEffect(() => {
        if (parameter) {
            setBody(parameter);
        }
    }, [parameter]);

    const handleChange = (
        field: keyof Omit<ParameterEntity, "id">,
        value: string
    ) => {
        setBody((prev) => ({...prev, [field]: value}));
        setErrors((prev) => ({...prev, [field]: false}));
    };

    const validateAndSubmit = () => {
        const newErrors: typeof errors = {};
        if (!body.path?.trim()) newErrors.path = true;
        if (!body.type) newErrors.type = true;
        if (body.value === undefined || body.value === null) newErrors.value = true;

        setErrors(newErrors);
        if (Object.keys(newErrors).length > 0) return;

        const action = parameter
            ? update.mutateAsync({body})
            : create.mutateAsync({body});

        action.then(() => setOpen(false));
    };

    return (
        <Dialog open={open} onOpenChange={setOpen}>
            <DialogTrigger asChild>
                <Button size="sm">{parameter ? "Edit" : "New Parameter"}</Button>
            </DialogTrigger>
            <DialogContent>
                <DialogTitle>
                    {parameter ? "Edit Parameter" : "New Parameter"}
                </DialogTitle>

                <Label>Path</Label>
                <Input
                    placeholder="e.g., config/server/timeout"
                    value={body.path ?? ""}
                    className={errors.path ? "border-red-500" : ""}
                    onChange={(e) => handleChange("path", e.target.value)}
                />

                <Label>Type</Label>
                <Select
                    value={body.type ?? ""}
                    onValueChange={(e) => handleChange("type", e as ParameterType)}
                >
                    <SelectTrigger className="w-full">
                        <SelectValue placeholder="Select parameter type"/>
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="STRING">STRING</SelectItem>
                        <SelectItem value="INTEGER">INTEGER</SelectItem>
                        <SelectItem value="FLOAT">FLOAT</SelectItem>
                        <SelectItem value="BOOLEAN">BOOLEAN</SelectItem>
                    </SelectContent>
                </Select>

                <Label>Value</Label>
                <Input
                    placeholder="Value"
                    value={body.value ?? ""}
                    className={errors.value ? "border-red-500" : ""}
                    onChange={(e) => handleChange("value", e.target.value)}
                />

                <Button onClick={validateAndSubmit}>
                    {parameter ? "Save" : "Create"}
                </Button>
            </DialogContent>
        </Dialog>
    );
}
